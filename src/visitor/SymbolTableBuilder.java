package visitor;

import ast.*;
import ast.python.*;
import symbol.*;

public class SymbolTableBuilder extends AbstractASTVisitor {
    private final SymbolTable table = new SymbolTable();

    public SymbolTable build(ASTNode root) {
        table.enterScope("global");
        if (root != null) root.accept(this);
        return table;
    }

    @Override
    public void visit(FunctionDefNode node) {
        table.define(new Symbol(node.getName(), SymbolKind.PYTHON_FUNCTION, node.getLine(), table.currentScope()));
        table.enterScope("function:" + node.getName());

        for (String param : node.getParams())
            table.define(new Symbol(param, SymbolKind.PARAMETER, node.getLine(), table.currentScope()));

        visitChildren(node);
        table.exitScope();
    }

    @Override
    public void visit(AssignmentNode node) {
        table.define(new Symbol(node.getName(), SymbolKind.PYTHON_VARIABLE, node.getLine(), table.currentScope()));
        visitChildren(node);
    }

    @Override
    public void visit(ForNode node) {
        table.define(new Symbol(node.getVar(), SymbolKind.PYTHON_VARIABLE, node.getLine(), table.currentScope()));
        visitChildren(node);
    }

    @Override
    public void visit(NameNode node) {
        table.define(new Symbol(node.getName(), SymbolKind.PYTHON_VARIABLE, node.getLine(), table.currentScope()));
    }

    @Override
    public void visit(ImportNode node) {
        for (String name : node.getNames())
            table.define(new Symbol(name, SymbolKind.PYTHON_IMPORT, node.getLine(), table.currentScope()));
    }

    @Override
    public void visit(LiteralNode node) {  }

    @Override
    public void visit(HtmlTagNode node) {
        table.define(new Symbol(node.getTagName(), SymbolKind.HTML_TAG, node.getLine(), table.currentScope()));
        table.enterScope("html:" + node.getTagName());
        visitChildren(node);
        table.exitScope();
    }

    @Override
    public void visit(TextNode node) {  }

    @Override
    public void visit(JinjaExprNode node) {
        table.define(new Symbol(node.getBase(), SymbolKind.JINJA_VARIABLE, node.getLine(), table.currentScope()));
    }

    @Override
    public void visit(JinjaForNode node) {
        table.enterScope("jinja-for:" + node.getVariable());
        table.define(new Symbol(node.getVariable(), SymbolKind.JINJA_LOOP_VAR, node.getLine(), table.currentScope()));
        visitChildren(node);
        table.exitScope();
    }

    @Override
    public void visit(JinjaIfNode node) {
        table.enterScope("jinja-if");
        visitChildren(node);
        table.exitScope();
    }

    @Override
    public void visit(JinjaBlockNode node) {
        table.define(new Symbol(node.getBlockName(), SymbolKind.JINJA_BLOCK, node.getLine(), table.currentScope()));
        table.enterScope("jinja-block:" + node.getBlockName());
        visitChildren(node);
        table.exitScope();
    }

    @Override
    public void visit(JinjaExtendsNode node) {
        table.define(new Symbol(node.getParentTemplate(), SymbolKind.JINJA_TEMPLATE, node.getLine(), table.currentScope()));
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
}
