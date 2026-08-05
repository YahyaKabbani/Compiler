package visitor;

import ast.ASTNode;
import ast.HtmlAttributeNode;
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

public class HtmlGenerator extends AbstractASTVisitor {
    private static final Set<String> VOID_ELEMENTS =
            Set.of("br", "hr", "img", "input", "link", "meta");

    private static final Set<String> LINK_ATTRIBUTES = Set.of("href", "action");

    private final String templateName;
    private final Map<String, String> links;
    private final Deque<Map<String, ASTNode>> scopes = new ArrayDeque<>();
    private final StringBuilder out = new StringBuilder();
    private final List<String> log = new ArrayList<>();
    private StringBuilder attributeValue;

    public HtmlGenerator(String templateName, Map<String, ASTNode> context) {
        this(templateName, context, Map.of());
    }

    public HtmlGenerator(String templateName, Map<String, ASTNode> context, Map<String, String> links) {
        this.templateName = templateName;
        this.links = links == null ? Map.of() : links;
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
            emitBody(node);
            return;
        }

        out.append('<').append(tag);
        emitAttributes(node);
        out.append('>');

        if (VOID_ELEMENTS.contains(tag.toLowerCase())) {
            out.append('\n');
            return;
        }

        emitBody(node);
        out.append("</").append(tag).append(">\n");
    }

    @Override
    public void visit(TextNode node) {
        if (attributeValue != null) {
            attributeValue.append(node.getText());
            return;
        }
        emitInline(node.getText());
    }

    @Override
    public void visit(JinjaExprNode node) {
        ASTNode value = resolveExpr(node.getBase(), node.getPath());
        if (value == null) {
            warn(node.getLine(), "{{ " + node.getExpression() + " }} unresolved"
                    + (attributeValue != null ? " in attribute" : ""));
            return;
        }
        String text = unquote(value.describe());
        if (attributeValue != null) {
            attributeValue.append(text);
            info("attr", node.getLine(), "{{ " + node.getExpression() + " }} -> " + text);
            return;
        }
        emitInline(text);
        info("expr", node.getLine(), "{{ " + node.getExpression() + " }} -> " + text);
    }

    private void emitBody(HtmlTagNode node) {
        for (ASTNode child : node.getChildren()) child.accept(this);
    }

    private void emitAttributes(HtmlTagNode node) {
        List<HtmlAttributeNode> nodes = node.getAttributeNodes();
        if (nodes.isEmpty()) {
            for (Map.Entry<String, String> attribute : node.getAttributes().entrySet()) {
                out.append(' ').append(attribute.getKey());
                if (!attribute.getValue().isEmpty()) out.append('=').append(attribute.getValue());
            }
            return;
        }

        for (HtmlAttributeNode attribute : nodes) {
            out.append(' ').append(attribute.getName());
            if (attribute.getRawValue().isEmpty()) continue;

            attributeValue = new StringBuilder();
            for (ASTNode part : attribute.getValueParts()) part.accept(this);
            String value = attributeValue.toString();
            attributeValue = null;

            out.append('=').append(mapLink(attribute.getName(), value, attribute.getLine()));
        }
    }

    private String mapLink(String name, String value, int line) {
        if (links.isEmpty() || !LINK_ATTRIBUTES.contains(name.toLowerCase())) return value;

        String quote = "";
        String url = value;
        if (url.length() >= 2 && (url.startsWith("\"") || url.startsWith("'"))) {
            quote = url.substring(0, 1);
            url = url.substring(1, url.length() - 1);
        }

        String mapped = links.get(url);
        if (mapped == null) return value;

        info("link", line, url + " -> " + mapped);
        return quote + mapped + quote;
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
