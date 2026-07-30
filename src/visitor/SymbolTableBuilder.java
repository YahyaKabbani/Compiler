package visitor;

import ast.*;
import ast.python.*;
import symbol.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

            // {{ variable }} inside attribute values, e.g. src="/x/{{ p.photo }}"
            for (String attrValue : tag.getAttributes().values()) {
                Matcher m = JINJA_EXPR_VAR.matcher(attrValue);
                while (m.find()) {
                    table.define(new Symbol(
                            m.group(1),
                            SymbolKind.JINJA_VARIABLE,
                            tag.getLine(),
                            table.currentScope()
                    ));
                }
            }

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

        if (node instanceof AssignmentNode a) {
            table.define(new Symbol(
                    a.getName(),
                    SymbolKind.PYTHON_VARIABLE,
                    a.getLine(),
                    table.currentScope()
            ));
            visit(a.getValue());
        }

        if (node instanceof ReturnNode r) {
            visit(r.getValue());
        }

        if (node instanceof CallNode c) {
            visit(c.getTarget());
            for (ASTNode arg : c.getArgs()) visit(arg);
        }

        if (node instanceof BinaryOpNode b) {
            visit(b.getLeft());
            visit(b.getRight());
        }

        if (node instanceof AttributeNode a) {
            visit(a.getObject());
        }

        if (node instanceof IfNode ifn) {
            visit(ifn.condition);
            for (ASTNode stmt : ifn.body) visit(stmt);
        }

        if (node instanceof ast.python.ForNode f) {
            table.define(new Symbol(
                    f.var,
                    SymbolKind.PYTHON_VARIABLE,
                    f.getLine(),
                    table.currentScope()
            ));
            visit(f.iterable);
            for (ASTNode stmt : f.body) visit(stmt);
        }

        // ---------- HTML {% for %} (built by ForLoopTransformer) ----------
        if (node instanceof ast.ForNode f) {
            table.define(new Symbol(
                    f.getVariable(),
                    SymbolKind.JINJA_LOOP_VAR,
                    f.getLine(),
                    table.currentScope()
            ));

            table.enterScope("html-for:" + f.getVariable());
            for (ASTNode child : f.getBody()) visit(child);
            table.exitScope();
        }

        // ---------- CSS ----------
        if (node instanceof CssStylesheetNode sheet) {
            for (ASTNode child : sheet.children) {
                visit(child); // child is CssRuleSetNode
            }
        }

        if (node instanceof CssRuleSetNode rule) {
            // a selector may be a comma-separated group of descendant chains,
            // e.g. ".navbar a:hover, input" → navbar (class), a (tag), input (tag)
            for (String group : rule.selector.split(",")) {
                for (String part : group.trim().split("\\s+")) {
                    String name = part.replaceAll(":.*$", "").trim(); // drop pseudo-class
                    if (name.isEmpty()) continue;

                    if (name.startsWith(".")) {
                        table.define(new Symbol(name.substring(1), SymbolKind.CSS_CLASS, rule.getLine(), table.currentScope()));
                    } else if (name.startsWith("#")) {
                        table.define(new Symbol(name.substring(1), SymbolKind.CSS_ID, rule.getLine(), table.currentScope()));
                    } else {
                        table.define(new Symbol(name, SymbolKind.CSS_TAG, rule.getLine(), table.currentScope()));
                    }
                }
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
        Matcher m = JINJA_FOR_VAR.matcher(content);
        return m.find() ? m.group(1) : null;
    }

    // root identifier of a jinja expression: {{ p.photo }} → p
    private static final Pattern JINJA_EXPR_VAR =
            Pattern.compile("\\{\\{\\s*([A-Za-z_]\\w*)");

    // loop variable of a jinja for: {% for index in items %} → index
    private static final Pattern JINJA_FOR_VAR =
            Pattern.compile("\\bfor\\s+([A-Za-z_]\\w*)\\s+in\\b");
}
