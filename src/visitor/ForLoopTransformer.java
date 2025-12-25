package visitor;

import ast.*;
import java.util.*;

public class ForLoopTransformer {

    public static HtmlDocumentNode transform(HtmlDocumentNode doc) {

        List<ASTNode> result = new ArrayList<>();
        List<ASTNode> nodes = doc.getChildren();

        for (int i = 0; i < nodes.size(); i++) {
            ASTNode node = nodes.get(i);

            // Detect {% for %}
            if (node instanceof JinjaNode j && j.isForStart()) {

                // Parse: {% for p in products %}
                String stmt = j.getContent()
                        .replace("{%", "")
                        .replace("%}", "")
                        .trim();

                String[] parts = stmt.split("\\s+");
                String variable = parts[1];
                String iterable = parts[3];

                List<ASTNode> body = new ArrayList<>();
                i++; // move past for

                // Collect body until {% endfor %}
                while (i < nodes.size()) {
                    ASTNode current = nodes.get(i);

                    if (current instanceof JinjaNode end && end.isForEnd()) {
                        break;
                    }

                    body.add(current);
                    i++;
                }

                result.add(new ForNode(variable, iterable, body, node.getLine()));
            } else {
                result.add(node);
            }
        }

        return new HtmlDocumentNode(result, doc.getLine());
    }
}
