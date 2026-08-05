package visitor;

import ast.ASTNode;
import ast.HtmlDocumentNode;
import ast.JinjaBlockNode;
import ast.JinjaExtendsNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TemplateInheritanceResolver {

    private TemplateInheritanceResolver() { }

    public static ASTNode resolve(String templateName, Map<String, ASTNode> templates) {
        return resolve(templateName, templates, new HashSet<>());
    }

    public static boolean isParentTemplate(String templateName, Map<String, ASTNode> templates) {
        for (Map.Entry<String, ASTNode> entry : templates.entrySet()) {
            if (entry.getKey().equals(templateName)) continue;
            if (templateName.equals(parentOf(entry.getValue()))) return true;
        }
        return false;
    }

    private static ASTNode resolve(String templateName, Map<String, ASTNode> templates, Set<String> visiting) {
        ASTNode template = templates.get(templateName);
        if (template == null) return null;

        if (!visiting.add(templateName)) return stripExtends(template);

        String parentName = parentOf(template);
        if (parentName == null) {
            visiting.remove(templateName);
            return template;
        }

        ASTNode parent = resolve(parentName, templates, visiting);
        visiting.remove(templateName);

        if (parent == null) return stripExtends(template);

        Map<String, JinjaBlockNode> overrides = collectBlocks(template);
        for (JinjaBlockNode target : collectBlocks(parent).values()) {
            JinjaBlockNode override = overrides.get(target.getBlockName());
            if (override != null) target.setBody(override.getBody());
        }

        return stripExtends(parent);
    }

    private static String parentOf(ASTNode template) {
        ExtendsCollector collector = new ExtendsCollector();
        template.accept(collector);
        return collector.found == null ? null : collector.found.getParentTemplate();
    }

    private static Map<String, JinjaBlockNode> collectBlocks(ASTNode template) {
        BlockCollector collector = new BlockCollector();
        template.accept(collector);
        return collector.byName;
    }

    private static ASTNode stripExtends(ASTNode template) {
        ExtendsCollector collector = new ExtendsCollector();
        template.accept(collector);
        if (collector.found != null) template.accept(new ExtendsRemover(collector.found));
        return template;
    }

    private static final class ExtendsCollector extends AbstractASTVisitor {
        private JinjaExtendsNode found;

        @Override
        public void visit(JinjaExtendsNode node) {
            if (found == null) found = node;
        }
    }

    private static final class BlockCollector extends AbstractASTVisitor {
        private final Map<String, JinjaBlockNode> byName = new LinkedHashMap<>();

        @Override
        public void visit(JinjaBlockNode node) {
            byName.putIfAbsent(node.getBlockName(), node);
            visitChildren(node);
        }
    }

    private static final class ExtendsRemover extends AbstractASTVisitor {
        private final ASTNode target;

        ExtendsRemover(ASTNode target) {
            this.target = target;
        }

        @Override
        public void visit(HtmlDocumentNode node) {
            List<ASTNode> kept = new ArrayList<>();
            for (ASTNode child : node.getChildren()) {
                if (child != target) kept.add(child);
            }
            node.setChildren(kept);
            visitChildren(node);
        }
    }
}
