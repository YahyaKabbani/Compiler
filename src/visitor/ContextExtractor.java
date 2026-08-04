package visitor;

import ast.*;
import ast.python.*;

import java.util.*;

public class ContextExtractor implements ASTVisitor {

    private final Map<String, Map<String, String>> contexts = new LinkedHashMap<>();

    public Map<String, Map<String, String>> extract(ASTNode root) {
        root.accept(this);
        return contexts;
    }

    // ==================== PYTHON ====================

    @Override
    public void visit(PythonProgramNode node) {
        for (ASTNode s : node.getStatements()) s.accept(this);
    }

    @Override
    public void visit(FunctionDefNode node) {
        for (ASTNode s : node.getBody()) s.accept(this);
    }

    @Override
    public void visit(ReturnNode node) {
        if (node.getValue() != null) node.getValue().accept(this);
    }

    @Override
    public void visit(AssignmentNode node) {
        if (node.getValue() != null) node.getValue().accept(this);
    }

    @Override
    public void visit(CallNode node) {
        tryExtractRenderTemplate(node);
        node.getTarget().accept(this);
        for (ASTNode arg : node.getArgs()) arg.accept(this);
    }

    @Override
    public void visit(IfNode node) {
        for (ASTNode s : node.getBody()) s.accept(this);
    }

    @Override
    public void visit(ast.python.ForNode node) {
        for (ASTNode s : node.getBody()) s.accept(this);
    }

    @Override public void visit(BinaryOpNode node)      { node.getLeft().accept(this); node.getRight().accept(this); }
    @Override public void visit(AttributeNode node)     { node.getObject().accept(this); }
    @Override public void visit(NameNode node)          { /* no-op */ }
    @Override public void visit(LiteralNode node)       { /* no-op */ }
    @Override public void visit(KeywordArgumentNode node) { if (node.getValue() != null) node.getValue().accept(this); }

    // ==================== HTML & CSS — not used ====================

    @Override public void visit(HtmlDocumentNode node)  { /* no-op */ }
    @Override public void visit(HtmlTagNode node)       { /* no-op */ }
    @Override public void visit(JinjaNode node)         { /* no-op */ }
    @Override public void visit(TextNode node)          { /* no-op */ }
    @Override public void visit(ast.ForNode node)       { /* no-op */ }
    @Override public void visit(CssStylesheetNode node) { /* no-op */ }
    @Override public void visit(CssRuleSetNode node)    { /* no-op */ }
    @Override public void visit(CssDeclarationNode node){ /* no-op */ }

    // ==================== Helpers ====================

    private void tryExtractRenderTemplate(CallNode call) {
        String funcName = resolveName(call.getTarget());
        if (!"render_template".equals(funcName)) return;

        List<ASTNode> args = call.getArgs();
        if (args.isEmpty()) return;

        String templateName = resolveValue(args.get(0));
        if (templateName == null) return;
        templateName = templateName.replaceAll("^\"|\"$|^'|'$", "");

        Map<String, String> ctx = contexts.computeIfAbsent(templateName, k -> new LinkedHashMap<>());

        int positionalIndex = 1;
        for (int i = 1; i < args.size(); i++) {
            ASTNode arg = args.get(i);
            if (arg instanceof KeywordArgumentNode kw) {
                ctx.put(kw.getName(), resolveValue(kw.getValue()));
            } else {
                ctx.put("arg" + positionalIndex++, resolveValue(arg));
            }
        }
    }

    private String resolveName(ASTNode node) {
        if (node instanceof NameNode n)      return n.getName();
        if (node instanceof AttributeNode a) return a.getAttribute();
        return null;
    }

    private String resolveValue(ASTNode node) {
        if (node instanceof KeywordArgumentNode kw) return resolveValue(kw.getValue());
        if (node instanceof LiteralNode l)           return l.getValue();
        if (node instanceof NameNode n)              return n.getName();
        if (node instanceof CallNode c)              return resolveName(c.getTarget()) + "(...)";
        if (node instanceof AttributeNode a)         return resolveValue(a.getObject()) + "." + a.getAttribute();
        return null;
    }

    public void dump() {
        System.out.println("===== CONTEXT EXTRACTOR =====");
        if (contexts.isEmpty()) {
            System.out.println("  (no render_template calls found)");
            return;
        }
        for (var entry : contexts.entrySet()) {
            System.out.println("  Template: " + entry.getKey());
            for (var v : entry.getValue().entrySet()) {
                System.out.println("    " + v.getKey() + " = " + v.getValue());
            }
        }
    }
}
