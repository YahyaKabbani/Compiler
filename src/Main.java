    import ast.ASTNode;
    import ast.HtmlDocumentNode;
    import visitor.CSSASTBuilder;
    import visitor.FlaskPythonASTBuilder;
    import visitor.ForLoopTransformer;
    import visitor.HTMLASTBuilder;

    import org.antlr.v4.runtime.*;
    import org.antlr.v4.runtime.tree.*;
    import gen.HTMLJinja2Parser;
    import gen.HTMLJinja2Lexer;
    import gen.CSSLexer;
    import gen.CSSParser;
    import gen.FlaskPythonParser;
    import gen.FlaskPythonLexer;
    import java.nio.file.Files;
    import java.nio.file.Path;

    public class Main {

        public static void main(String[] args) throws Exception {

            String input = Files.readString(Path.of("app.py"));
          //  System.out.println(input);
            CharStream cs = CharStreams.fromString(input);

            FlaskPythonLexer lexer = new FlaskPythonLexer(cs);
          //  System.out.println(lexer);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
          //  System.out.println(tokens);

            FlaskPythonParser parser = new FlaskPythonParser(tokens);
           // System.out.println(parser);
            ParseTree tree = parser.program();
           // System.out.println(tree);
            FlaskPythonASTBuilder builder = new FlaskPythonASTBuilder();
           // System.out.println(builder);
            ASTNode ast = builder.visit(tree);
           // System.out.println(ast);
            System.out.println("===== PYTHON AST =====");
            if (ast != null) ast.print("");
            else System.out.println("AST is null");






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
