package visitor;

import ast.*;
import gen.HTMLJinja2Parser;
import gen.HTMLJinja2ParserBaseVisitor;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLASTBuilder extends HTMLJinja2ParserBaseVisitor<ASTNode> {
    private static final Pattern JINJA_EXPR = Pattern.compile("\\{\\{.*?\\}\\}");

    @Override
    public ASTNode visitHtmlDocument(HTMLJinja2Parser.HtmlDocumentContext ctx) {
        return new HtmlDocumentNode(visitAll(ctx.children), ctx.start.getLine());
    }

    @Override
    public ASTNode visitHtmlElement(HTMLJinja2Parser.HtmlElementContext ctx) {
        if (ctx.TAG_OPEN() != null && ctx.TAG_NAME() != null && !ctx.TAG_NAME().isEmpty()) {
            String tag = ctx.TAG_NAME(0).getText();

            List<HtmlAttributeNode> attributes = new ArrayList<>();
            for (var attrCtx : ctx.htmlAttribute()) {
                int attrLine = attrCtx.start.getLine();
                String attrName = attrCtx.TAG_NAME().getText();
                String attrValue = attrCtx.ATTVALUE_VALUE() != null
                        ? attrCtx.ATTVALUE_VALUE().getText()
                        : "";
                attributes.add(new HtmlAttributeNode(
                        attrName, attrValue, splitAttributeValue(attrValue, attrLine), attrLine));
            }

            List<ASTNode> children = new ArrayList<>();
            if (ctx.htmlContent() != null && ctx.htmlContent().children != null) {
                children = visitAll(ctx.htmlContent().children);
            }

            return HtmlTagNode.withAttributes(tag, attributes, children, ctx.start.getLine());
        }

        if (ctx.jinja() != null) return visit(ctx.jinja());

        if (ctx.script() != null) return new TextNode(ctx.getText(), ctx.start.getLine());
        if (ctx.style() != null)  return new TextNode(ctx.getText(), ctx.start.getLine());

        return null;
    }

    @Override
    public ASTNode visitHtmlChardata(HTMLJinja2Parser.HtmlChardataContext ctx) {
        TextNode text = new TextNode(ctx.getText(), ctx.start.getLine());

        return text.isBlank() ? null : text;
    }

    @Override
    public ASTNode visitHtmlElements(HTMLJinja2Parser.HtmlElementsContext ctx) {
        List<ASTNode> nodes = visitAll(ctx.children);
        if (nodes.size() == 1) return nodes.get(0);
        return new HtmlTagNode("__group__", nodes, ctx.start.getLine());
    }

    @Override
    public ASTNode visitJinja(HTMLJinja2Parser.JinjaContext ctx) {
        String raw = ctx.getText();
        int line = ctx.start.getLine();

        if (ctx.JINJA_EXPR() != null)    return new JinjaExprNode(raw, line);
        if (ctx.JINJA_COMMENT() != null) return new JinjaCommentNode(raw, line);
        if (ctx.JINJA_STMT() != null)    return buildStatement(raw, line);

        return null;
    }

    private ASTNode buildStatement(String raw, int line) {
        String body = raw.replace("{%", "").replace("%}", "").trim();
        String[] parts = body.split("\\s+");
        String keyword = parts.length > 0 ? parts[0] : "";

        switch (keyword) {
            case "for":

                if (parts.length >= 4) {
                    return new JinjaForNode(parts[1], parts[3], new ArrayList<>(), raw, line);
                }
                return new JinjaRawStmtNode(keyword, raw, line);

            case "endfor":
                return new JinjaEndForNode(raw, line);

            case "if":
                return new JinjaIfNode(joinFrom(parts, 1), new ArrayList<>(), raw, line);

            case "endif":
                return new JinjaEndIfNode(raw, line);

            case "block":
                return new JinjaBlockNode(
                        parts.length > 1 ? parts[1] : "",
                        new ArrayList<>(), raw, line);

            case "endblock":
                return new JinjaEndBlockNode(raw, line);

            case "extends":
                return new JinjaExtendsNode(unquote(joinFrom(parts, 1)), raw, line);

            default:
                return new JinjaRawStmtNode(keyword, raw, line);
        }
    }

    private List<ASTNode> splitAttributeValue(String value, int line) {
        List<ASTNode> parts = new ArrayList<>();
        if (value.isEmpty()) return parts;

        Matcher matcher = JINJA_EXPR.matcher(value);
        int last = 0;
        while (matcher.find()) {
            if (matcher.start() > last) {
                parts.add(TextNode.raw(value.substring(last, matcher.start()), line));
            }
            parts.add(new JinjaExprNode(matcher.group(), line));
            last = matcher.end();
        }
        if (last < value.length()) parts.add(TextNode.raw(value.substring(last), line));

        return parts;
    }

    private List<ASTNode> visitAll(List<? extends ParseTree> children) {
        List<ASTNode> nodes = new ArrayList<>();
        if (children == null) return nodes;
        for (ParseTree child : children) {
            ASTNode node = visit(child);
            if (node != null) nodes.add(node);
        }
        return nodes;
    }

    private static String joinFrom(String[] parts, int from) {
        return String.join(" ", java.util.Arrays.asList(parts).subList(
                Math.min(from, parts.length), parts.length));
    }

    private static String unquote(String s) {
        return s.replaceAll("^\"|\"$|^'|'$", "");
    }
}
