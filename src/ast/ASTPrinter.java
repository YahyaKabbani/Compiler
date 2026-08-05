package ast;

import java.util.List;

public class ASTPrinter {
    public static void printTree(ASTNode node) {
        if (node == null) {
            System.out.println("!!! Empty Tree !!!");
            return;
        }
        System.out.println(node.label());
        printChildren(node.children(), "");
    }

    public static void printChildren(List<ASTNode> children, String indent) {
        if (children == null || children.isEmpty()) return;

        for (int i = 0; i < children.size(); i++) {
            boolean isLast = (i == children.size() - 1);
            ASTNode child = children.get(i);

            System.out.println(indent + (isLast ? "└── " : "├── ") + child.label());
            printChildren(child.children(), indent + (isLast ? "    " : "│   "));
        }
    }
}
