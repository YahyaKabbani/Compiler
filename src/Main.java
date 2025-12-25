import ast.ASTNode;
import ast.HtmlDocumentNode;
import visitor.CSSASTBuilder;
import visitor.ForLoopTransformer;
import visitor.HTMLASTBuilder;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import gen.HTMLJinja2Parser;
import gen.HTMLJinja2Lexer;
import gen.CSSLexer;
import gen.CSSParser;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {

        String input = Files.readString(Path.of("add.html"));
        System.out.println(input);
        CharStream charStream = CharStreams.fromString(input);
        System.out.println(charStream);

        HTMLJinja2Lexer lexer = new HTMLJinja2Lexer(charStream);
        System.out.println(lexer);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        System.out.println(tokens);

        HTMLJinja2Parser parser = new HTMLJinja2Parser(tokens);
        System.out.println(parser);


       ParseTree tree = parser.htmlDocument();
        System.out.println(tree);

HTMLASTBuilder builder = new HTMLASTBuilder();
        System.out.println(builder);

       ASTNode ast = builder.visitHtmlDocument((HTMLJinja2Parser.HtmlDocumentContext) tree);
        System.out.println(ast);

       HtmlDocumentNode transformed =
               ForLoopTransformer.transform((HtmlDocumentNode) ast);

       System.out.println("===== FINAL HTML AST =====");
        transformed.print("");




/*
        String input = Files.readString(Path.of("test.css"));
        CharStream cs = CharStreams.fromString(input);

        CSSLexer lexer = new CSSLexer(cs);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        CSSParser parser = new CSSParser(tokens);

        ParseTree tree = parser.stylesheet();

        CSSASTBuilder builder = new CSSASTBuilder();

        ASTNode ast = builder.visit(tree);

        System.out.println("===== CSS AST =====");
        if (ast != null) ast.print("");
        else System.out.println("AST is null");





*/













    }
}
