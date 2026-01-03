package ast;

import java.util.List;

public class ASTPrinter {
    public static void printTree(ASTNode node) {
        if (node != null) {
            node.print("");
        } else {
            System.out.println("!!! Empty Tree !!!");
        }
    }

    public static void printChildren(List<ASTNode> children, String indent) {
        if (children == null) return;
        for (int i = 0; i < children.size(); i++) {
            boolean isLast = (i == children.size() - 1);
            System.out.print(indent + (isLast ? "└── " : "├── "));
            children.get(i).print(indent + (isLast ? "    " : "│   "));
        }
    }
}