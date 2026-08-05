import ast.ASTNode;
import ast.ASTPrinter;
import gen.CSSLexer;
import gen.CSSParser;
import gen.FlaskPythonLexer;
import gen.FlaskPythonParser;
import gen.HTMLJinja2Lexer;
import gen.HTMLJinja2Parser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import output.CompilerOutputWriter;
import output.OutputWriter;
import symbol.SymbolTable;
import visitor.CSSASTBuilder;
import visitor.ContextExtractor;
import visitor.FlaskPythonASTBuilder;
import visitor.HTMLASTBuilder;
import visitor.HtmlGenerator;
import visitor.JinjaBlockBuilder;
import visitor.JinjaContextLinker;
import visitor.SemanticAnalyzer;
import visitor.SymbolTableBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String PYTHON_FILE = "app.py";
    private static final Path TEMPLATE_DIR = Path.of("templates");
    private static final Path CSS_FILE = Path.of("static", "style.css");

    public static void main(String[] args) throws Exception {
        ASTNode pythonAst = buildPythonAst(PYTHON_FILE);

        banner("PYTHON AST — " + PYTHON_FILE);
        ASTPrinter.printTree(pythonAst);

        banner("SYMBOL TABLE — " + PYTHON_FILE);
        dumpSymbols(pythonAst);

        ContextExtractor extractor = new ContextExtractor();
        Map<String, Map<String, ASTNode>> contexts = extractor.extract(pythonAst);

        banner("GENERATOR — DATA EXTRACTED FROM " + PYTHON_FILE);
        extractor.dump();

        SemanticAnalyzer analyzer = new SemanticAnalyzer(TEMPLATE_DIR);
        analyzer.analyzePython(pythonAst, PYTHON_FILE);

        for (Path template : findTemplates()) {
            String name = template.getFileName().toString();

            List<ASTNode> unclosed = new ArrayList<>();
            ASTNode templateAst = buildTemplateAst(template, unclosed);

            Map<String, ASTNode> context = contexts.get(name);
            JinjaContextLinker.link(templateAst, context);

            analyzer.analyzeTemplate(templateAst, name);
            analyzer.reportUnclosed(name, unclosed);

            banner("TEMPLATE AST — " + name
                    + (context == null ? "  (no python context)" : "  (linked)"));
            ASTPrinter.printTree(templateAst);

            banner("SYMBOL TABLE — " + name);
            dumpSymbols(templateAst);
        }

        if (Files.exists(CSS_FILE)) {
            ASTNode cssAst = buildCssAst(CSS_FILE);

            banner("CSS AST — " + CSS_FILE);
            ASTPrinter.printTree(cssAst);

            banner("SYMBOL TABLE — " + CSS_FILE);
            dumpSymbols(cssAst);
        }

        banner("SEMANTIC ANALYSIS");
        analyzer.writeReport(Path.of("compiler_output", "semantic_report.txt"));

        banner("CODE GENERATION");
        generate(pythonAst, contexts);
    }

    private static void generate(ASTNode pythonAst, Map<String, Map<String, ASTNode>> contexts) throws Exception {
        OutputWriter.clean();

        Map<String, ASTNode> generationTrees = new LinkedHashMap<>();
        for (Path template : findTemplates()) {
            String name = template.getFileName().toString();
            generationTrees.put(name, buildTemplateAst(template, new ArrayList<>()));
        }

        List<String> log = new ArrayList<>();
        for (Map.Entry<String, ASTNode> entry : generationTrees.entrySet()) {
            String name = entry.getKey();

            HtmlGenerator generator = new HtmlGenerator(name, contexts.get(name));
            String html = generator.generate(entry.getValue());
            OutputWriter.writePage(name, html);

            String pageName = name.replace(".jinja", ".html");
            String pageLine = String.format("[page]  %s -> output/%s (%d bytes)",
                    name, pageName, html.getBytes().length);
            log.add(pageLine);
            log.addAll(generator.getLog());
            System.out.println(pageLine);
        }

        OutputWriter.copySupportFiles();
        CompilerOutputWriter.writeAstJson("ast_python.json", pythonAst);
        CompilerOutputWriter.writeAstJson("ast_jinja.json", generationTrees);
        CompilerOutputWriter.writeGenerationLog(log);
        System.out.println("[copy]  app.py, style.css -> output/");
        System.out.println("[json]  ast_python.json, ast_jinja.json, generation_log.txt -> compiler_output/");
    }

    private static List<Path> findTemplates() throws Exception {
        if (!Files.isDirectory(TEMPLATE_DIR)) {
            System.out.println("\n(no templates/ directory found)");
            return List.of();
        }
        try (var entries = Files.list(TEMPLATE_DIR)) {
            return entries.filter(p -> p.getFileName().toString().endsWith(".jinja"))
                    .sorted()
                    .toList();
        }
    }

    private static ASTNode buildPythonAst(String file) throws Exception {
        String source = Files.readString(Path.of(file));
        FlaskPythonLexer lexer = new FlaskPythonLexer(CharStreams.fromString(source));
        FlaskPythonParser parser = new FlaskPythonParser(new CommonTokenStream(lexer));
        return new FlaskPythonASTBuilder().visit(parser.program());
    }

    private static ASTNode buildTemplateAst(Path file, List<ASTNode> unclosed) throws Exception {
        String source = Files.readString(file);
        HTMLJinja2Lexer lexer = new HTMLJinja2Lexer(CharStreams.fromString(source));
        HTMLJinja2Parser parser = new HTMLJinja2Parser(new CommonTokenStream(lexer));
        ASTNode ast = new HTMLASTBuilder().visit(parser.htmlDocument());

        return JinjaBlockBuilder.build(ast, unclosed);
    }

    private static ASTNode buildCssAst(Path file) throws Exception {
        String source = Files.readString(file);
        CSSLexer lexer = new CSSLexer(CharStreams.fromString(source));
        CSSParser parser = new CSSParser(new CommonTokenStream(lexer));
        return new CSSASTBuilder().visit(parser.stylesheet());
    }

    private static void dumpSymbols(ASTNode ast) {
        SymbolTable table = new SymbolTableBuilder().build(ast);
        table.dumpAll();
    }

    private static void banner(String title) {
        System.out.println();
        System.out.println("========== " + title + " ==========");
    }
}
