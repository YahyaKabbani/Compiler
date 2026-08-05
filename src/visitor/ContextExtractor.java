package visitor;

import ast.ASTNode;
import ast.python.AssignmentNode;
import ast.python.CallNode;
import ast.python.FunctionDefNode;
import ast.python.ReturnNode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ContextExtractor extends AbstractASTVisitor {
    private final Map<String, Map<String, ASTNode>> contexts = new LinkedHashMap<>();

    private final Map<String, ASTNode> variables = new LinkedHashMap<>();

    private final Map<String, FunctionDefNode> functions = new LinkedHashMap<>();

    private final Map<String, ASTNode> loopVariables = new LinkedHashMap<>();

    private static final int MAX_RESOLVE_DEPTH = 5;

    public Map<String, Map<String, ASTNode>> extract(ASTNode root) {
        contexts.clear();
        variables.clear();
        functions.clear();
        loopVariables.clear();

        if (root == null) return contexts;

        root.accept(new DeclarationCollector());

        root.accept(this);

        return contexts;
    }

    public Map<String, Map<String, ASTNode>> getContexts() { return contexts; }

    private class DeclarationCollector extends AbstractASTVisitor {
        @Override
        public void visit(AssignmentNode node) {
            variables.putIfAbsent(node.getName(), node.getValue());
            visitChildren(node);
        }

        @Override
        public void visit(FunctionDefNode node) {
            functions.put(node.getName(), node);
            visitChildren(node);
        }

        @Override
        public void visit(ast.python.ForNode node) {
            loopVariables.putIfAbsent(node.getVar(), node.getIterable());
            visitChildren(node);
        }
    }

    @Override
    public void visit(CallNode node) {
        if ("render_template".equals(node.calledFunctionName())) {
            recordTemplate(node);
        }
        visitChildren(node);
    }

    private void recordTemplate(CallNode call) {
        List<ASTNode> args = call.getArgs();
        if (args.isEmpty()) return;

        String templateName = unquote(args.get(0).describe());
        if (templateName == null || templateName.isEmpty()) return;

        Map<String, ASTNode> ctx =
                contexts.computeIfAbsent(templateName, k -> new LinkedHashMap<>());

        int positional = 1;
        for (int i = 1; i < args.size(); i++) {
            ASTNode arg = args.get(i);

            String name = arg.keywordName();
            if (name == null) name = "arg" + positional++;

            ctx.put(name, resolveData(arg.keywordValue(), 0));
        }
    }

    private ASTNode resolveData(ASTNode value, int depth) {
        if (value == null || depth >= MAX_RESOLVE_DEPTH) return value;
        if (value.isDataValue()) return value;

        String variable = value.asVariableName();
        if (variable != null) {
            ASTNode bound = variables.get(variable);
            if (bound != null && bound != value) {
                ASTNode resolved = resolveData(bound, depth + 1);
                if (resolved != null && resolved.isDataValue()) return resolved;
            }

            ASTNode iterable = loopVariables.get(variable);
            if (iterable != null) {
                ASTNode source = resolveData(iterable, depth + 1);
                ASTNode element = source != null ? source.elementType() : null;
                if (element != null) return element;
            }

            return bound != null ? bound : value;
        }

        String function = value.calledFunctionName();
        if (function != null) {
            FunctionDefNode def = functions.get(function);
            if (def != null) {
                ASTNode returned = resolveFromReturns(def, depth);
                if (returned != null) return returned;
            }
        }

        return value;
    }

    private ASTNode resolveFromReturns(FunctionDefNode def, int depth) {
        ReturnCollector collector = new ReturnCollector();
        def.accept(collector);

        for (ASTNode returned : collector.values) {
            ASTNode resolved = resolveData(returned, depth + 1);
            if (resolved != null && resolved.isDataValue()) return resolved;
        }
        return null;
    }

    private static class ReturnCollector extends AbstractASTVisitor {
        final List<ASTNode> values = new ArrayList<>();

        @Override
        public void visit(ReturnNode node) {
            if (node.getValue() != null) values.add(node.getValue());
            visitChildren(node);
        }
    }

    private static String unquote(String s) {
        return s == null ? null : s.replaceAll("^\"|\"$|^'|'$", "");
    }

    public void dump() {
        System.out.println("===== CONTEXT EXTRACTOR (Python → Jinja) =====");
        if (contexts.isEmpty()) {
            System.out.println("  (no render_template calls found)");
            return;
        }
        for (Map.Entry<String, Map<String, ASTNode>> entry : contexts.entrySet()) {
            System.out.println("  Template: " + entry.getKey());
            for (Map.Entry<String, ASTNode> v : entry.getValue().entrySet()) {
                ASTNode value = v.getValue();
                System.out.println("    " + v.getKey() + " = "
                        + (value == null ? "null" : value.describe())
                        + (value != null && value.isDataValue() ? "   [resolved data]" : ""));
            }
        }
    }
}
