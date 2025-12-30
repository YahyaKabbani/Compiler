package visitor;

import ast.*;
import ast.python.*;
import symbol.*;

public class SymbolTableBuilder {

    private final SymbolTable table = new SymbolTable();

    public SymbolTable build(ASTNode root) {
        table.enterScope("global");
        visit(root);
        return table;
    }

    private void visit(ASTNode node) {
        if (node == null) return;

        /* ---------- HTML ---------- */

        if (node instanceof HtmlDocumentNode doc) {
            for (ASTNode c : doc.getChildren()) visit(c);
        }

        if (node instanceof HtmlTagNode tag) {
            table.define(new Symbol(
                    tag.getTagName(),
                    SymbolKind.HTML_TAG,
                    tag.getLine(),
                    table.currentScope()
            ));

            table.enterScope("html:" + tag.getTagName());
            for (ASTNode c : tag.getChildren()) visit(c);
            table.exitScope();
        }

        /* ---------- JINJA ---------- */

        if (node instanceof JinjaNode j) {

            // {{ variable }}
            if (j.getType().equals("EXPR")) {
                String var = extractName(j.getContent());
                if (var != null) {
                    table.define(new Symbol(
                            var,
                            SymbolKind.JINJA_VARIABLE,
                            j.getLine(),
                            table.currentScope()
                    ));
                }
            }

            // {% for x in items %}
            if (j.isForStart()) {
                table.enterScope("jinja-for");

                String loopVar = extractForVariable(j.getContent());
                if (loopVar != null) {
                    table.define(new Symbol(
                            loopVar,
                            SymbolKind.JINJA_LOOP_VAR,
                            j.getLine(),
                            table.currentScope()
                    ));
                }
            }

            if (j.isForEnd()) {
                table.exitScope();
            }
        }

        /* ---------- PYTHON / FLASK ---------- */

        if (node instanceof PythonProgramNode p) {
            for (ASTNode s : p.getStatements()) visit(s);
        }

        if (node instanceof FunctionDefNode f) {
            table.define(new Symbol(
                    f.getName(),
                    SymbolKind.PYTHON_FUNCTION,
                    f.getLine(),
                    table.currentScope()
            ));

            table.enterScope("function:" + f.getName());

            for (String param : f.getParams()) {
                table.define(new Symbol(
                        param,
                        SymbolKind.PARAMETER,
                        f.getLine(),
                        table.currentScope()
                ));
            }

            for (ASTNode stmt : f.getBody()) visit(stmt);

            table.exitScope();
        }

        if (node instanceof NameNode n) {
            table.define(new Symbol(
                    n.getName(),
                    SymbolKind.PYTHON_VARIABLE,
                    n.getLine(),
                    table.currentScope()
            ));
        }


        // ---------- CSS ----------
        if (node instanceof CssStylesheetNode sheet) {
            for (ASTNode child : sheet.children) {
                visit(child); // child is CssRuleSetNode
            }
        }

        if (node instanceof CssRuleSetNode rule) {
            String selector = rule.selector;

            // determine symbol kind
            if (selector.startsWith(".")) {
                table.define(new Symbol(selector.substring(1), SymbolKind.CSS_CLASS, rule.getLine(), table.currentScope()));
            } else if (selector.startsWith("#")) {
                table.define(new Symbol(selector.substring(1), SymbolKind.CSS_ID, rule.getLine(), table.currentScope()));
            } else {
                table.define(new Symbol(selector, SymbolKind.CSS_TAG, rule.getLine(), table.currentScope()));
            }
        }


    }

    /* ---------- Helpers ---------- */

    private String extractName(String content) {
        // {{ user.name }} → user
        content = content.replaceAll("[{}]", "").trim();
        if (content.contains(".")) {
            return content.split("\\.")[0];
        }
        return content;
    }

    private String extractForVariable(String content) {
        // {% for item in items %}
        int forIdx = content.indexOf("for");
        int inIdx = content.indexOf("in");
        if (forIdx == -1 || inIdx == -1) return null;
        return content.substring(forIdx + 3, inIdx).trim();
    }
}
