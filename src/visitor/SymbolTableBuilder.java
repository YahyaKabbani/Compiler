package visitor;

import ast.*;
import ast.python.*;
import symbol.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SymbolTableBuilder implements ASTVisitor {

    private final SymbolTable table = new SymbolTable();

    public SymbolTable build(ASTNode root) {
        table.enterScope("global");
        root.accept(this);
        return table;
    }

    // ==================== PYTHON ====================

    @Override
    public void visit(PythonProgramNode node) {
        for (ASTNode s : node.getStatements()) s.accept(this);
    }

    @Override
    public void visit(FunctionDefNode node) {
        table.define(new Symbol(node.getName(), SymbolKind.PYTHON_FUNCTION, node.getLine(), table.currentScope()));
        table.enterScope("function:" + node.getName());
        for (String param : node.getParams())
            table.define(new Symbol(param, SymbolKind.PARAMETER, node.getLine(), table.currentScope()));
        for (ASTNode stmt : node.getBody()) stmt.accept(this);
        table.exitScope();
    }

    @Override
    public void visit(AssignmentNode node) {
        table.define(new Symbol(node.getName(), SymbolKind.PYTHON_VARIABLE, node.getLine(), table.currentScope()));
        if (node.getValue() != null) node.getValue().accept(this);
    }

    @Override
    public void visit(ReturnNode node) {
        if (node.getValue() != null) node.getValue().accept(this);
    }

    @Override
    public void visit(CallNode node) {
        node.getTarget().accept(this);
        for (ASTNode arg : node.getArgs()) arg.accept(this);
    }

    @Override
    public void visit(BinaryOpNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visit(AttributeNode node) {
        node.getObject().accept(this);
    }

    @Override
    public void visit(IfNode node) {
        node.getCondition().accept(this);
        for (ASTNode stmt : node.getBody()) stmt.accept(this);
    }

    @Override
    public void visit(ast.python.ForNode node) {
        table.define(new Symbol(node.getVar(), SymbolKind.PYTHON_VARIABLE, node.getLine(), table.currentScope()));
        node.getIterable().accept(this);
        for (ASTNode stmt : node.getBody()) stmt.accept(this);
    }

    @Override
    public void visit(NameNode node) {
        table.define(new Symbol(node.getName(), SymbolKind.PYTHON_VARIABLE, node.getLine(), table.currentScope()));
    }

    @Override
    public void visit(LiteralNode node) { /* no symbols */ }

    @Override
    public void visit(KeywordArgumentNode node) {
        if (node.getValue() != null) node.getValue().accept(this);
    }

    // ==================== HTML ====================

    @Override
    public void visit(HtmlDocumentNode node) {
        for (ASTNode c : node.getChildren()) c.accept(this);
    }

    @Override
    public void visit(HtmlTagNode node) {
        table.define(new Symbol(node.getTagName(), SymbolKind.HTML_TAG, node.getLine(), table.currentScope()));
        table.enterScope("html:" + node.getTagName());

        for (String attrValue : node.getAttributes().values()) {
            Matcher m = JINJA_EXPR_VAR.matcher(attrValue);
            while (m.find())
                table.define(new Symbol(m.group(1), SymbolKind.JINJA_VARIABLE, node.getLine(), table.currentScope()));
        }

        for (ASTNode c : node.getChildren()) c.accept(this);
        table.exitScope();
    }

    @Override
    public void visit(JinjaNode node) {
        if (node.getType().equals("EXPR")) {
            String var = extractName(node.getContent());
            if (var != null)
                table.define(new Symbol(var, SymbolKind.JINJA_VARIABLE, node.getLine(), table.currentScope()));
        }
        if (node.isForStart()) {
            table.enterScope("jinja-for");
            String loopVar = extractForVariable(node.getContent());
            if (loopVar != null)
                table.define(new Symbol(loopVar, SymbolKind.JINJA_LOOP_VAR, node.getLine(), table.currentScope()));
        }
        if (node.isForEnd()) {
            table.exitScope();
        }
    }

    @Override
    public void visit(TextNode node) { /* no symbols */ }

    @Override
    public void visit(ast.ForNode node) {
        table.define(new Symbol(node.getVariable(), SymbolKind.JINJA_LOOP_VAR, node.getLine(), table.currentScope()));
        table.enterScope("html-for:" + node.getVariable());
        for (ASTNode child : node.getBody()) child.accept(this);
        table.exitScope();
    }

    // ==================== CSS ====================

    @Override
    public void visit(CssStylesheetNode node) {
        for (ASTNode child : node.getChildren()) child.accept(this);
    }

    @Override
    public void visit(CssRuleSetNode node) {
        for (String group : node.getSelector().split(",")) {
            for (String part : group.trim().split("\\s+")) {
                String name = part.replaceAll(":.*$", "").trim();
                if (name.isEmpty()) continue;
                if (name.startsWith("."))
                    table.define(new Symbol(name.substring(1), SymbolKind.CSS_CLASS, node.getLine(), table.currentScope()));
                else if (name.startsWith("#"))
                    table.define(new Symbol(name.substring(1), SymbolKind.CSS_ID, node.getLine(), table.currentScope()));
                else
                    table.define(new Symbol(name, SymbolKind.CSS_TAG, node.getLine(), table.currentScope()));
            }
        }
    }

    @Override
    public void visit(CssDeclarationNode node) { /* no symbols */ }

    // ==================== Helpers ====================

    private String extractName(String content) {
        content = content.replaceAll("[{}]", "").trim();
        return content.contains(".") ? content.split("\\.")[0] : content;
    }

    private String extractForVariable(String content) {
        Matcher m = JINJA_FOR_VAR.matcher(content);
        return m.find() ? m.group(1) : null;
    }

    private static final Pattern JINJA_EXPR_VAR = Pattern.compile("\\{\\{\\s*([A-Za-z_]\\w*)");
    private static final Pattern JINJA_FOR_VAR  = Pattern.compile("\\bfor\\s+([A-Za-z_]\\w*)\\s+in\\b");
}
