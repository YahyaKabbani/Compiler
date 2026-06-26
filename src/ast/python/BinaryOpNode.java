package ast.python;

import ast.ASTNode;

public class BinaryOpNode extends ASTNode {

    private final ASTNode left;
    private final String operator;
    private final ASTNode right;

    public BinaryOpNode(ASTNode left, String operator, ASTNode right, int line) {
        super("BinaryOp", line);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "BinaryOp " + operator);
        left.print(indent + "  ");
        right.print(indent + "  ");
    }

    public ASTNode getLeft() {
        return left;
    }

    public ASTNode getRight() {
        return right;
    }
}
