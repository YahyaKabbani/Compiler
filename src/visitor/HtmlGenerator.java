package visitor;

import ast.ASTNode;
import ast.HtmlTagNode;
import ast.JinjaExprNode;
import ast.JinjaForNode;
import ast.JinjaIfNode;
import ast.TextNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlGenerator extends AbstractASTVisitor {
    private static final Set<String> VOID_ELEMENTS =
            Set.of("br", "hr", "img", "input", "link", "meta");

    private static final Pattern ATTRIBUTE_EXPR = Pattern.compile("\\{\\{[^}]*\\}\\}");

    private final String templateName;
    private final Deque<Map<String, ASTNode>> scopes = new ArrayDeque<>();
    private final StringBuilder out = new StringBuilder();
    private final List<String> log = new ArrayList<>();

    public HtmlGenerator(String templateName, Map<String, ASTNode> context) {
        this.templateName = templateName;
        Map<String, ASTNode> global = new LinkedHashMap<>();
        if (context != null) global.putAll(context);
        scopes.push(global);
    }

    public static String generate(ASTNode template, Map<String, ASTNode> context) {
        return new HtmlGenerator("", context).generate(template);
    }

    public String generate(ASTNode template) {
        if (template != null) template.accept(this);
        return out.toString();
    }

    public List<String> getLog() { return log; }

    @Override
    public void visit(HtmlTagNode node) {
        String tag = node.getTagName();
        if ("__group__".equals(tag)) {
            visitChildren(node);
            return;
        }

        out.append('<').append(tag);
        for (Map.Entry<String, String> attribute : node.getAttributes().entrySet()) {
            out.append(' ').append(attribute.getKey());
            if (!attribute.getValue().isEmpty()) {
                out.append('=').append(substituteAttribute(attribute.getValue(), node.getLine()));
            }
        }
        out.append('>');

        if (VOID_ELEMENTS.contains(tag.toLowerCase())) {
            out.append('\n');
            return;
        }

        visitChildren(node);
        out.append("</").append(tag).append(">\n");
    }

    @Override
    public void visit(TextNode node) {
        emitInline(node.getText());
    }

    @Override
    public void visit(JinjaExprNode node) {
        ASTNode value = resolveExpr(node.getBase(), node.getPath());
        if (value == null) {
            warn(node.getLine(), "{{ " + node.getExpression() + " }} unresolved");
            return;
        }
        String text = unquote(value.describe());
        emitInline(text);
        info("expr", node.getLine(), "{{ " + node.getExpression() + " }} -> " + text);
    }

    @Override
    public void visit(JinjaForNode node) {
        ASTNode source = resolve(node.getIterable());
        if (source == null || !source.isDataValue()) {
            warn(node.getLine(), "{% for " + node.getVariable() + " in "
                    + node.getIterable() + " %} has no resolved data");
            return;
        }

        List<ASTNode> elements = source.children();
        info("loop", node.getLine(), "{% for " + node.getVariable() + " in "
                + node.getIterable() + " %} -> " + elements.size() + " iterations");

        for (ASTNode element : elements) {
            Map<String, ASTNode> scope = new LinkedHashMap<>();
            scope.put(node.getVariable(), element);
            scopes.push(scope);
            visitChildren(node);
            scopes.pop();
        }
    }

    @Override
    public void visit(JinjaIfNode node) {
        String base = node.getCondition().split("\\s+")[0];
        String[] parts = base.split("\\.");

        ASTNode value = resolve(parts[0]);
        if (value == null) {
            info("if", node.getLine(), "{% if " + node.getCondition() + " %} unresolved -> emitted");
            visitChildren(node);
            return;
        }

        for (int i = 1; i < parts.length && value != null; i++) {
            value = value.lookup(parts[i]);
        }

        if (truthy(value)) {
            info("if", node.getLine(), "{% if " + node.getCondition() + " %} -> true");
            visitChildren(node);
        } else {
            info("if", node.getLine(), "{% if " + node.getCondition() + " %} -> false, skipped");
        }
    }

    private String substituteAttribute(String value, int line) {
        if (!value.contains("{{")) return value;

        StringBuilder sb = new StringBuilder();
        Matcher matcher = ATTRIBUTE_EXPR.matcher(value);
        while (matcher.find()) {
            JinjaExprNode expr = new JinjaExprNode(matcher.group(), line);
            ASTNode resolved = resolveExpr(expr.getBase(), expr.getPath());
            String replacement;
            if (resolved == null) {
                warn(line, "{{ " + expr.getExpression() + " }} unresolved in attribute");
                replacement = "";
            } else {
                replacement = unquote(resolved.describe());
                info("attr", line, "{{ " + expr.getExpression() + " }} -> " + replacement);
            }
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private void emitInline(String text) {
        if (out.length() > 0) {
            char last = out.charAt(out.length() - 1);
            if (last != '>' && last != '$' && last != '/' && !Character.isWhitespace(last)) {
                out.append(' ');
            }
        }
        out.append(text);
    }

    private ASTNode resolve(String name) {
        if (name == null) return null;
        for (Map<String, ASTNode> scope : scopes) {
            ASTNode value = scope.get(name);
            if (value != null) return value;
        }
        return null;
    }

    private ASTNode resolveExpr(String base, List<String> path) {
        ASTNode value = resolve(base);
        for (String key : path) {
            if (value == null) return null;
            value = value.lookup(key);
        }
        return value;
    }

    private static boolean truthy(ASTNode value) {
        if (value == null) return false;
        String text = value.describe();
        return !text.equals("None") && !text.equals("False")
                && !text.equals("[0 elements]") && !text.equals("\"\"")
                && !text.equals("''") && !text.isEmpty();
    }

    private static String unquote(String s) {
        return s == null ? "" : s.replaceAll("^\"|\"$|^'|'$", "");
    }

    private void info(String kind, int line, String message) {
        log.add(String.format("[%s]  %s:%d  %s", kind, templateName, line, message));
    }

    private void warn(int line, String message) {
        log.add(String.format("[warn]  %s:%d  %s", templateName, line, message));
    }
}
