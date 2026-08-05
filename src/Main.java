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
import symbol.SymbolTable;
import visitor.CSSASTBuilder;
import visitor.ContextExtractor;
import visitor.FlaskPythonASTBuilder;
import visitor.HTMLASTBuilder;
import visitor.JinjaBlockBuilder;
import visitor.JinjaContextLinker;
import visitor.SymbolTableBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
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

        for (Path template : findTemplates()) {
            String name = template.getFileName().toString();

            ASTNode templateAst = buildTemplateAst(template);

            Map<String, ASTNode> context = contexts.get(name);
            JinjaContextLinker.link(templateAst, context);

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

    private static ASTNode buildTemplateAst(Path file) throws Exception {
        String source = Files.readString(file);
        HTMLJinja2Lexer lexer = new HTMLJinja2Lexer(CharStreams.fromString(source));
        HTMLJinja2Parser parser = new HTMLJinja2Parser(new CommonTokenStream(lexer));
        ASTNode ast = new HTMLASTBuilder().visit(parser.htmlDocument());

        return JinjaBlockBuilder.build(ast);
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
