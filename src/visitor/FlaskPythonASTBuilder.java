package visitor;

import ast.ASTNode;
import ast.python.*;
import gen.FlaskPythonParser;
import gen.FlaskPythonParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;

public class FlaskPythonASTBuilder extends FlaskPythonParserBaseVisitor<ASTNode> {

    // ================= PROGRAM =================

    @Override
    public ASTNode visitProgram(FlaskPythonParser.ProgramContext ctx) {
        List<ASTNode> statements = new ArrayList<>();

        for (var stmt : ctx.statement()) {
            ASTNode node = visit(stmt);
            if (node != null) statements.add(node);
        }

        return new PythonProgramNode(statements, ctx.start.getLine());
    }

    // ================= STATEMENT DISPATCH =================

    @Override
    public ASTNode visitStatement(FlaskPythonParser.StatementContext ctx) {
        if (ctx.simpleStmt() != null) return visit(ctx.simpleStmt());
        if (ctx.compoundStmt() != null) return visit(ctx.compoundStmt());
        return null;
    }

    // ================= FUNCTIONS =================

    @Override
    public ASTNode visitFunctionDef(FlaskPythonParser.FunctionDefContext ctx) {
        String name = ctx.IDENT().getText();
        int line = ctx.start.getLine();

        List<String> params = new ArrayList<>();
        if (ctx.paramList() != null) {
            for (var id : ctx.paramList().IDENT()) params.add(id.getText());
        }

        List<ASTNode> body = new ArrayList<>();
        for (var st : ctx.suite().statement()) {
            ASTNode node = visit(st);
            if (node != null) body.add(node);
        }

        return new FunctionDefNode(
                name,
                params,
                body,
                List.of(), // decorators later (optional)
                line
        );
    }

    // ================= ASSIGNMENT =================

    @Override
    public ASTNode visitAssignment(FlaskPythonParser.AssignmentContext ctx) {
        return new AssignmentNode(
                ctx.IDENT().getText(),
                visit(ctx.expr()),
                ctx.start.getLine()
        );
    }

    // ================= RETURN =================

    @Override
    public ASTNode visitReturnStmt(FlaskPythonParser.ReturnStmtContext ctx) {
        return new ReturnNode(
                visit(ctx.expr()),
                ctx.start.getLine()
        );
    }

    // ================= ARGUMENT =================
    // IMPORTANT: ensures args always become AST nodes (especially keyword args)
    @Override
    public ASTNode visitArgument(FlaskPythonParser.ArgumentContext ctx) {
        // keyword arg: IDENT ASSIGN expr  (e.g. debug=True)
        if (ctx.IDENT() != null && ctx.ASSIGN() != null) {
            // If you don't have a KeywordArgNode class, just return the value for now:
            return visit(ctx.expr());
        }
        // positional arg: expr
        return visit(ctx.expr());
    }

    // ================= EXPRESSIONS =================

    @Override
    public ASTNode visitExpr(FlaskPythonParser.ExprContext ctx) {

        // ---------- BASE CASE ----------
        if (ctx.atom() != null) {
            return visit(ctx.atom());
        }

        // ---------- ATTRIBUTE ACCESS ----------
        if (ctx.DOT() != null) {
            ASTNode obj = visit(ctx.expr(0));
            return new AttributeNode(
                    obj,
                    ctx.IDENT().getText(),
                    ctx.start.getLine()
            );
        }

        // ---------- FUNCTION CALL ----------
        if (ctx.LPAREN() != null) {
            ASTNode target = visit(ctx.expr(0));

            if (target == null) {
                throw new RuntimeException(
                        "Call target is null at line " + ctx.start.getLine()
                                + " text=" + ctx.getText()
                );
            }

            List<ASTNode> args = new ArrayList<>();
            if (ctx.argList() != null) {
                for (var a : ctx.argList().argument()) {
                    ASTNode arg = visit(a);
                    if (arg != null) args.add(arg);
                }
            }

            return new CallNode(target, args, ctx.start.getLine());
        }

        // ---------- BINARY OPERATORS ----------
        if (ctx.expr().size() == 2) {
            return new BinaryOpNode(
                    visit(ctx.expr(0)),
                    ctx.getChild(1).getText(),
                    visit(ctx.expr(1)),
                    ctx.start.getLine()
            );
        }

        return null;
    }

    // ================= ATOMS =================
    // CRITICAL: without this, Name/Flask/__name__ becomes null -> your crash
    @Override
    public ASTNode visitAtom(FlaskPythonParser.AtomContext ctx) {
        int line = ctx.start.getLine();

        if (ctx.IDENT() != null) return new NameNode(ctx.IDENT().getText(), line);
        if (ctx.STRING() != null) return new LiteralNode(ctx.STRING().getText(), line);
        if (ctx.NUMBER() != null) return new LiteralNode(ctx.NUMBER().getText(), line);
        if (ctx.TRUE() != null) return new LiteralNode("True", line);
        if (ctx.FALSE() != null) return new LiteralNode("False", line);
        if (ctx.NONE() != null) return new LiteralNode("None", line);

        // If you later implement dict/list nodes properly, you can override those visitors too.
        if (ctx.dictLiteral() != null) return visit(ctx.dictLiteral());
        if (ctx.listLiteral() != null) return visit(ctx.listLiteral());

        if (ctx.expr() != null) return visit(ctx.expr()); // (expr)

        return null;
    }

    @Override
    public ASTNode visitIfStmt(FlaskPythonParser.IfStmtContext ctx) {
        ASTNode condition = visit(ctx.expr());

        List<ASTNode> body = new ArrayList<>();
        for (var st : ctx.suite().statement()) {
            ASTNode node = visit(st);
            if (node != null) body.add(node);
        }

        return new IfNode(condition, body, ctx.start.getLine());
    }
    @Override
    public ASTNode visitForStmt(FlaskPythonParser.ForStmtContext ctx) {
        String var = ctx.IDENT().getText();
        ASTNode iterable = visit(ctx.expr());

        List<ASTNode> body = new ArrayList<>();
        for (var st : ctx.suite().statement()) {
            ASTNode node = visit(st);
            if (node != null) body.add(node);
        }

        return new ForNode(var, iterable, body, ctx.start.getLine());
    }

}
