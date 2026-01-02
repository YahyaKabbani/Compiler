import ast.ASTNode;
import ast.ASTPrinter;
import ast.HtmlDocumentNode;
import gen.CSSLexer;
import gen.CSSParser;
import gen.HTMLJinja2Lexer;
import gen.HTMLJinja2Parser;
import gen.FlaskPythonLexer;
import gen.FlaskPythonParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import visitor.CSSASTBuilder;
import visitor.HTMLASTBuilder;
import visitor.FlaskPythonASTBuilder;
import visitor.ForLoopTransformer;
import symbol.SymbolTable;
import visitor.SymbolTableBuilder;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {

        String filePath = "base.html";

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

        } else if (filePath.endsWith(".py")) {
            FlaskPythonLexer lexer = new FlaskPythonLexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            FlaskPythonParser parser = new FlaskPythonParser(tokens);
            ParseTree tree = parser.program();
            FlaskPythonASTBuilder builder = new FlaskPythonASTBuilder();
            ast = builder.visit(tree);
        } else {
            System.out.println("الملف غير مدعوم!");
            return;
        }

        if (ast != null) {

            System.out.println(" SYMBOL TABLE ========================");
            SymbolTableBuilder stb = new SymbolTableBuilder();
            SymbolTable table = stb.build(ast);
            table.dumpAll();

            System.out.println(" ABSTRACT SYNTAX TREE (AST) ==========================");
            ASTPrinter.printTree(ast);

        } else {
            System.out.println("(AST is null)");
        }
    }
}