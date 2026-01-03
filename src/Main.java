import ast.ASTNode;
//import ast.ASTPrinter;
import ast.HtmlDocumentNode;
import gen.CSSLexer;
import gen.CSSParser;
import gen.HTMLJinja2Lexer;
import gen.HTMLJinja2Parser;
import gen.FlaskPythonParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import symbol.SymbolTable;
import visitor.CSSASTBuilder;
import visitor.ForLoopTransformer;
import visitor.HTMLASTBuilder;
import visitor.SymbolTableBuilder;
import visitor.FlaskPythonASTBuilder;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {




//
//            String html = Files.readString(Path.of("base.html"));
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