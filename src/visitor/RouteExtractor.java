package visitor;

import ast.ASTNode;
import ast.python.CallNode;
import ast.python.FunctionDefNode;

import java.util.LinkedHashMap;
import java.util.Map;

public final class RouteExtractor extends AbstractASTVisitor {
    private final Map<String, String> routes = new LinkedHashMap<>();

    public static Map<String, String> extract(ASTNode pythonAst) {
        RouteExtractor extractor = new RouteExtractor();
        if (pythonAst != null) pythonAst.accept(extractor);
        return extractor.routes;
    }

    @Override
    public void visit(FunctionDefNode node) {
        String route = routeOf(node);
        String template = templateOf(node);
        if (route != null && template != null) routes.putIfAbsent(route, template);
        visitChildren(node);
    }

    private static String routeOf(FunctionDefNode node) {
        RouteCall call = new RouteCall();
        for (ASTNode decorator : node.getDecorators()) decorator.accept(call);
        return call.path;
    }

    private static String templateOf(FunctionDefNode node) {
        TemplateCall call = new TemplateCall();
        for (ASTNode statement : node.getBody()) statement.accept(call);
        return call.template;
    }

    private static final class RouteCall extends AbstractASTVisitor {
        private String path;

        @Override
        public void visit(CallNode node) {
            if (path == null && "route".equals(node.calledFunctionName()) && !node.getArgs().isEmpty()) {
                path = unquote(node.getArgs().get(0).describe());
            }
            visitChildren(node);
        }
    }

    private static final class TemplateCall extends AbstractASTVisitor {
        private String template;

        @Override
        public void visit(CallNode node) {
            if (template == null && "render_template".equals(node.calledFunctionName())
                    && !node.getArgs().isEmpty()) {
                template = unquote(node.getArgs().get(0).describe());
            }
            visitChildren(node);
        }
    }

    private static String unquote(String s) {
        return s == null ? null : s.replaceAll("^\"|\"$|^'|'$", "");
    }
}
