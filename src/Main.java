import ast.ASTNode;
//import ast.ASTPrinter;
import ast.ASTPrinter;
import ast.HtmlDocumentNode;
import gen.CSSLexer;
import gen.CSSParser;
import gen.HTMLJinja2Lexer;
import gen.HTMLJinja2Parser;
import gen.FlaskPythonLexer;
import gen.FlaskPythonParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import symbol.SymbolTable;
import visitor.CSSASTBuilder;
import visitor.ForLoopTransformer;
import visitor.HTMLASTBuilder;
import visitor.JinjaContextLinker;
import visitor.SymbolTableBuilder;
import visitor.ContextExtractor;
import visitor.FlaskPythonASTBuilder;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {


        String filePath = "index.html";
//        String filePath = "app.py";
//        String filePath = "test.css";
        String input = Files.readString(Path.of(filePath));
        CharStream charStream = CharStreams.fromString(input);
        ASTNode ast = null;

        if (filePath.endsWith(".css")) {
            CSSLexer lexer = new CSSLexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CSSParser parser = new CSSParser(tokens);
            ParseTree tree = parser.stylesheet();
            CSSASTBuilder builder = new CSSASTBuilder();
            ast = builder.visit(tree);

        } else if (filePath.endsWith(".html")) {
            HTMLJinja2Lexer lexer = new HTMLJinja2Lexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            HTMLJinja2Parser parser = new HTMLJinja2Parser(tokens);
            ParseTree tree = parser.htmlDocument();
            HTMLASTBuilder builder = new HTMLASTBuilder();
            ast = builder.visit(tree);

            ast = ForLoopTransformer.transform((HtmlDocumentNode) ast);

            // ربط بيانات Python بشجرة HTML
            String pySource = Files.readString(Path.of("app.py"));
            FlaskPythonLexer pyLexer = new FlaskPythonLexer(CharStreams.fromString(pySource));
            FlaskPythonParser pyParser = new FlaskPythonParser(new CommonTokenStream(pyLexer));
            ASTNode pyAst = new FlaskPythonASTBuilder().visit(pyParser.program());

            ContextExtractor extractor = new ContextExtractor();
            String templateName = Path.of(filePath).getFileName().toString();
            java.util.Map<String, String> context = extractor.extract(pyAst).get(templateName);

            System.out.println(" PYTHON CONTEXT FOR " + templateName + " ==========================");
            if (context != null) {
                context.forEach((k, v) -> System.out.println("  " + k + " = " + v));
                JinjaContextLinker.link(ast, context);
            } else {
                System.out.println("  (no context found for " + templateName + ")");
            }

        } else if (filePath.endsWith(".py")) {
            FlaskPythonLexer lexer = new FlaskPythonLexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            FlaskPythonParser parser = new FlaskPythonParser(tokens);
            ParseTree tree = parser.program();
            FlaskPythonASTBuilder builder = new FlaskPythonASTBuilder();
            ast = builder.visit(tree);
        } else {
            System.out.println("File not supported");
            return;
        }

        if (ast != null) {


            System.out.println(" ABSTRACT SYNTAX TREE (AST) ==========================");
            ASTPrinter.printTree(ast);


            System.out.println(" SYMBOL TABLE ========================");
            SymbolTableBuilder stb = new SymbolTableBuilder();
            SymbolTable table = stb.build(ast);
            table.dumpAll();

            if (filePath.endsWith(".py")) {
                System.out.println(" CONTEXT EXTRACTOR ==========================");
                ContextExtractor extractor = new ContextExtractor();
                extractor.extract(ast);
                extractor.dump();
            }

        } else {
            System.out.println("(AST is null)");
        }


//
//
//
//            String html = Files.readString(Path.of("index.html"));
//            CharStream csHtml = CharStreams.fromString(html);
//
//            HTMLJinja2Lexer htmlLexer = new HTMLJinja2Lexer(csHtml);
//            CommonTokenStream htmlTokens = new CommonTokenStream(htmlLexer);
//            HTMLJinja2Parser htmlParser = new HTMLJinja2Parser(htmlTokens);
//
//            ParseTree htmlTree = htmlParser.htmlDocument();
//            HTMLASTBuilder htmlBuilder = new HTMLASTBuilder();
//            ASTNode htmlAst = htmlBuilder.visit(htmlTree);
//
//            System.out.println("\n===== HTML AST =====");
//            htmlAst.print("");
//
//            System.out.println("\n===== HTML SYMBOL TABLE =====");
//            SymbolTable htmlTable = new SymbolTableBuilder().build(htmlAst);
//            htmlTable.dumpAll();


//
//      String input = Files.readString(Path.of("app.py"));
//          //  System.out.println(input);
//            CharStream cs = CharStreams.fromString(input);
//
//            FlaskPythonLexer lexer = new FlaskPythonLexer(cs);
//          //  System.out.println(lexer);
//            CommonTokenStream tokens = new CommonTokenStream(lexer);
//          //  System.out.println(tokens);
//
//            FlaskPythonParser parser = new FlaskPythonParser(tokens);
//           // System.out.println(parser);
//            ParseTree tree = parser.program();
//           // System.out.println(tree);
//            FlaskPythonASTBuilder builder = new FlaskPythonASTBuilder();
//           // System.out.println(builder);
//            ASTNode ast = builder.visit(tree);
//
//            System.out.println("===== PYTHON AST =====");
//            if (ast != null) ast.print("");
//            else System.out.println("AST is null");
//
//            /* -------- SYMBOL TABLE TEST -------- */
//     System.out.println("\n===== SYMBOL TABLE =====");
//            SymbolTableBuilder stb = new SymbolTableBuilder();
//            SymbolTable table = stb.build(ast);
//            table.dumpAll();


//
//
//        String input = Files.readString(Path.of("test.css"));
//        CharStream cs = CharStreams.fromString(input);
//
//        CSSLexer lexer = new CSSLexer(cs);
//
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//
//        CSSParser parser = new CSSParser(tokens);
//
//        ParseTree tree = parser.stylesheet();
//
//        CSSASTBuilder builder = new CSSASTBuilder();
//
//        ASTNode ast = builder.visit(tree);
//
//        System.out.println("===== CSS AST =====");
//        if (ast != null) ast.print("");
//        else System.out.println("AST is null");
//        System.out.println("\n===== SYMBOL TABLE =====");
//        SymbolTableBuilder stb = new SymbolTableBuilder();
//        SymbolTable table = stb.build(ast);
//        table.dumpAll();
//


    }
}