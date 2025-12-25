package visitor;

import ast.*;
import gen.CSSParser;
import gen.CSSParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;

public class CSSASTBuilder extends CSSParserBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitStylesheet(CSSParser.StylesheetContext ctx) {
        List<ASTNode> children = new ArrayList<>();

        // stylesheet: ... (nestedStatement ...)* EOF
        // We only care about ruleset nodes inside nestedStatement.
        for (var st : ctx.nestedStatement()) {
            ASTNode n = visit(st);
            if (n != null) children.add(n);
        }

        return new CssStylesheetNode(children, ctx.start.getLine());
    }

    @Override
    public ASTNode visitNestedStatement(CSSParser.NestedStatementContext ctx) {
        // Only build AST for ruleset right now (most important part)
        if (ctx.ruleset() != null) return visit(ctx.ruleset());
        return null;
    }

    @Override
    public ASTNode visitKnownRuleset(CSSParser.KnownRulesetContext ctx) {
        String selector = ctx.selectorGroup().getText(); // compact text, OK for AST

        List<ASTNode> decls = new ArrayList<>();
        if (ctx.declarationList() != null) {
            // declarationList has many children; easiest is visit all declaration() nodes
            for (var d : ctx.declarationList().declaration()) {
                ASTNode dn = visit(d);
                if (dn != null) decls.add(dn);
            }
        }

        return new CssRuleSetNode(selector, decls, ctx.start.getLine());
    }

    @Override
    public ASTNode visitKnownDeclaration(CSSParser.KnownDeclarationContext ctx) {
        String prop = ctx.property_().getText();
        String val  = ctx.expr().getText(); // includes tokens without spaces, acceptable for now
        return new CssDeclarationNode(prop, val, ctx.start.getLine());
    }

    @Override
    public ASTNode visitUnknownDeclaration(CSSParser.UnknownDeclarationContext ctx) {
        // fallback if parser couldn't parse expr: store raw value
        String prop = ctx.property_().getText();
        String val  = ctx.value().getText();
        return new CssDeclarationNode(prop, val, ctx.start.getLine());
    }
}
