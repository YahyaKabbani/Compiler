package visitor;

import ast.ASTNode;
import ast.python.*;
import gen.FlaskPythonParser;
import gen.FlaskPythonParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;

public class FlaskPythonASTBuilder extends FlaskPythonParserBaseVisitor<ASTNode> {
    @Override
    public ASTNode visitProgram(FlaskPythonParser.ProgramContext ctx) {
        List<ASTNode> statements = new ArrayList<>();

        for (var stmt : ctx.statement()) {
            ASTNode node = visit(stmt);
            if (node != null) statements.add(node);
        }

        return new PythonProgramNode(statements, ctx.start.getLine());
    }

    @Override
    public ASTNode visitStatement(FlaskPythonParser.StatementContext ctx) {
        if (ctx.simpleStmt() != null) return visit(ctx.simpleStmt());
        if (ctx.compoundStmt() != null) return visit(ctx.compoundStmt());
        return null;
    }

    @Override
    public ASTNode visitImportStmt(FlaskPythonParser.ImportStmtContext ctx) {
        List<String> names = new ArrayList<>();
        for (var id : ctx.IDENT()) names.add(id.getText());
        return new ImportNode(null, names, ctx.start.getLine());
    }

    @Override
    public ASTNode visitFromImportStmt(FlaskPythonParser.FromImportStmtContext ctx) {
        List<String> names = new ArrayList<>();
        for (var id : ctx.IDENT()) names.add(id.getText());

        String module = names.isEmpty() ? null : names.remove(0);
        return new ImportNode(module, names, ctx.start.getLine());
    }

    @Override
    public ASTNode visitDecoratedDef(FlaskPythonParser.DecoratedDefContext ctx) {
        List<ASTNode> decorators = new ArrayList<>();
        for (var dec : ctx.decorator()) {
            ASTNode decNode = visit(dec.expr());
            if (decNode != null) decorators.add(decNode);
        }

        FunctionDefNode funcNode = (FunctionDefNode) visitFunctionDef(ctx.functionDef());
        return new FunctionDefNode(
                funcNode.getName(),
                funcNode.getParams(),
                funcNode.getBody(),
                decorators,
                funcNode.getLine()
        );
    }

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
                List.of(),
                line
        );
    }

    @Override
    public ASTNode visitAssignment(FlaskPythonParser.AssignmentContext ctx) {
        return new AssignmentNode(
                ctx.IDENT().getText(),
                visit(ctx.expr()),
                ctx.start.getLine()
        );
    }

    @Override
    public ASTNode visitReturnStmt(FlaskPythonParser.ReturnStmtContext ctx) {
        return new ReturnNode(
                visit(ctx.expr()),
                ctx.start.getLine()
        );
    }

    @Override
    public ASTNode visitArgument(FlaskPythonParser.ArgumentContext ctx) {
        if (ctx.IDENT() != null && ctx.ASSIGN() != null) {
            return new KeywordArgumentNode(
                    ctx.IDENT().getText(),
                    visit(ctx.expr()),
                    ctx.start.getLine()
            );
        }

        return visit(ctx.expr());
    }

    @Override
    public ASTNode visitExpr(FlaskPythonParser.ExprContext ctx) {
        if (ctx.atom() != null) {
            return visit(ctx.atom());
        }

        if (ctx.DOT() != null) {
            ASTNode obj = visit(ctx.expr(0));
            return new AttributeNode(
                    obj,
                    ctx.IDENT().getText(),
                    ctx.start.getLine()
            );
        }

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

        if (ctx.LBRACK() != null) {
            return new SubscriptNode(
                    visit(ctx.expr(0)),
                    visit(ctx.expr(1)),
                    ctx.start.getLine()
            );
        }

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

    @Override
    public ASTNode visitListLiteral(FlaskPythonParser.ListLiteralContext ctx) {
        List<ASTNode> elements = new ArrayList<>();
        for (var e : ctx.expr()) {
            ASTNode node = visit(e);
            if (node != null) elements.add(node);
        }
        return new ListNode(elements, ctx.start.getLine());
    }

    @Override
    public ASTNode visitDictLiteral(FlaskPythonParser.DictLiteralContext ctx) {
        List<DictEntryNode> entries = new ArrayList<>();
        for (var e : ctx.dictEntry()) {
            ASTNode node = visit(e);
            if (node != null) entries.add((DictEntryNode) node);
        }
        return new DictNode(entries, ctx.start.getLine());
    }

    @Override
    public ASTNode visitDictEntry(FlaskPythonParser.DictEntryContext ctx) {
        return new DictEntryNode(
                visit(ctx.expr(0)),
                visit(ctx.expr(1)),
                ctx.start.getLine()
        );
    }

    @Override
    public ASTNode visitAtom(FlaskPythonParser.AtomContext ctx) {
        int line = ctx.start.getLine();

        if (ctx.IDENT() != null) return new NameNode(ctx.IDENT().getText(), line);
        if (ctx.STRING() != null) return new LiteralNode(ctx.STRING().getText(), line);
        if (ctx.NUMBER() != null) return new LiteralNode(ctx.NUMBER().getText(), line);
        if (ctx.TRUE() != null) return new LiteralNode("True", line);
        if (ctx.FALSE() != null) return new LiteralNode("False", line);
        if (ctx.NONE() != null) return new LiteralNode("None", line);

        if (ctx.dictLiteral() != null) return visit(ctx.dictLiteral());
        if (ctx.listLiteral() != null) return visit(ctx.listLiteral());

        if (ctx.expr() != null) return visit(ctx.expr());

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
