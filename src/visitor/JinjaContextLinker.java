package visitor;

import ast.ASTNode;
import ast.JinjaExprNode;
import ast.JinjaForNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

public class JinjaContextLinker extends AbstractASTVisitor {
    private final Deque<Map<String, ASTNode>> scopes = new ArrayDeque<>();

    private JinjaContextLinker(Map<String, ASTNode> context) {
        Map<String, ASTNode> global = new LinkedHashMap<>();
        if (context != null) global.putAll(context);
        scopes.push(global);
    }

    public static void link(ASTNode templateRoot, Map<String, ASTNode> context) {
        if (templateRoot == null) return;
        templateRoot.accept(new JinjaContextLinker(context));
    }

    @Override
    public void visit(JinjaForNode node) {
        ASTNode source = resolve(node.getIterable());

        node.setResolvedData(source);
        node.setResolvedSource(source != null ? source.describe() : "unresolved");

        Map<String, ASTNode> scope = new LinkedHashMap<>();
        ASTNode element = source != null ? source.elementType() : null;
        if (element != null) scope.put(node.getVariable(), element);

        scopes.push(scope);
        visitChildren(node);
        scopes.pop();
    }

    @Override
    public void visit(JinjaExprNode node) {
        ASTNode value = resolve(node.getBase());

        for (String key : node.getPath()) {
            if (value == null) break;
            value = value.lookup(key);
        }

        node.setResolvedValue(value);
    }

    private ASTNode resolve(String name) {
        if (name == null) return null;
        for (Map<String, ASTNode> scope : scopes) {
            ASTNode value = scope.get(name);
            if (value != null) return value;
        }
        return null;
    }
}
