package visitor;

import ast.*;
import java.util.Map;

/**
 * يربط بيانات Python AST بشجرة HTML/Jinja2.
 *
 * مثال:
 *   Python:  render_template("index.html", products=read())
 *   Context: { "index.html": { "products": "read(...)" } }
 *
 *   HTML ForNode: FOR p IN products  [source: unresolved]
 *   بعد الربط:   FOR p IN products  [source: read(...)]
 */
public class JinjaContextLinker {

    /**
     * يمشي على HTML AST ويربط كل ForNode بمصدر بياناته من Python context.
     *
     * @param htmlRoot   جذر شجرة HTML
     * @param context    Map من ContextExtractor: { varName -> source }
     */
    public static void link(ASTNode htmlRoot, Map<String, String> context) {
        if (htmlRoot == null || context == null || context.isEmpty()) return;
        walk(htmlRoot, context);
    }

    private static void walk(ASTNode node, Map<String, String> context) {
        if (node == null) return;

        if (node instanceof HtmlDocumentNode doc) {
            for (ASTNode child : doc.getChildren()) walk(child, context);
        }

        else if (node instanceof ForNode forNode) {
            // iterable = "products" → ابحث عنها في context
            String source = context.get(forNode.getIterable());
            forNode.setResolvedSource(source != null ? source : "unresolved");
            for (ASTNode child : forNode.getBody()) walk(child, context);
        }

        else if (node instanceof HtmlTagNode tag) {
            for (ASTNode child : tag.getChildren()) walk(child, context);
        }
    }
}
