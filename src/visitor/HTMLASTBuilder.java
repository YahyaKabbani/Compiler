package visitor;
import java.util.*;
import ast.ASTNode;
import ast.HtmlDocumentNode;
import gen.HTMLJinja2Parser;
import gen.HTMLJinja2ParserBaseVisitor;
import ast.HtmlTagNode;
import ast.TextNode;
import ast.JinjaNode;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

public class HTMLASTBuilder
        extends HTMLJinja2ParserBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitHtmlDocument(HTMLJinja2Parser.HtmlDocumentContext ctx) {

        List<ASTNode> children = new ArrayList<>();

        for (var child : ctx.children) {
            ASTNode node = visit(child);
            if (node != null) {
                children.add(node);
            }
        }

        return new HtmlDocumentNode(children, ctx.start.getLine());
    }



    @Override
    public ASTNode visitHtmlElement(HTMLJinja2Parser.HtmlElementContext ctx) {

        // CASE 1: normal HTML tag
        if (ctx.TAG_OPEN() != null && ctx.TAG_NAME() != null && !ctx.TAG_NAME().isEmpty()) {

            String tag = ctx.TAG_NAME(0).getText();

            Map<String, String> attributes = new LinkedHashMap<>();
            for (var attrCtx : ctx.htmlAttribute()) {
                String attrName = attrCtx.TAG_NAME().getText();
                String attrValue = attrCtx.ATTVALUE_VALUE() != null
                        ? attrCtx.ATTVALUE_VALUE().getText()
                        : "";
                attributes.put(attrName, attrValue);
            }

            List<ASTNode> children = new ArrayList<>();

            if (ctx.htmlContent() != null && ctx.htmlContent().children != null) {
                for (ParseTree childCtx : ctx.htmlContent().children) {
                    ASTNode child = visit(childCtx);
                    if (child != null) {
                        children.add(child);
                    }
                }
            }


            return new HtmlTagNode(tag, attributes, children, ctx.start.getLine());
        }

        // CASE 2: jinja inside htmlElement
        if (ctx.jinja() != null) {
            return visit(ctx.jinja());
        }

        // CASE 3: script/style (optional later)
        if (ctx.script() != null) {
            return new TextNode(ctx.getText(), ctx.start.getLine());
        }

        if (ctx.style() != null) {
            return new TextNode(ctx.getText(), ctx.start.getLine());
        }

        // OTHERWISE: ignore
        return null;
    }

    public ASTNode visitHtmlChardata(HTMLJinja2Parser.HtmlChardataContext ctx) {
        return new TextNode(ctx.getText(), ctx.start.getLine());
    }
    public ASTNode visitJinja(HTMLJinja2Parser.JinjaContext ctx) {

        if (ctx.JINJA_EXPR() != null) {
            return new JinjaNode("EXPR", ctx.getText(), ctx.start.getLine());
        }

        if (ctx.JINJA_STMT() != null) {
            return new JinjaNode("STMT", ctx.getText(), ctx.start.getLine());
        }

        if (ctx.JINJA_COMMENT() != null) {
            return new JinjaNode("COMMENT", ctx.getText(), ctx.start.getLine());
        }

        return null;
    }
    @Override
    public ASTNode visitHtmlElements(HTMLJinja2Parser.HtmlElementsContext ctx) {

        List<ASTNode> nodes = new ArrayList<>();

        for (var child : ctx.children) {
            ASTNode n = visit(child);
            if (n != null) {
                nodes.add(n);
            }
        }

        // unwrap single real element
        if (nodes.size() == 1) {
            return nodes.get(0);
        }

        // group multiple nodes (safe fallback)
        return new HtmlTagNode("__group__", nodes, ctx.start.getLine());
    }

}
