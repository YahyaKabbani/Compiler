package ast.python;

import ast.ASTNode;

import java.util.List;

public class BinaryOpNode extends PythonNode {
    private final ASTNode left;
    private final String operator;
    private final ASTNode right;

    public BinaryOpNode(ASTNode left, String operator, ASTNode right, int line) {
        super("BinaryOp", line);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public ASTNode getLeft() { return left; }
    public String getOperator() { return operator; }
    public ASTNode getRight() { return right; }

    @Override
    public String label() { return at("BinaryOp '" + operator + "'"); }

    @Override
    public List<ASTNode> children() { return kids().add(left).add(right).build(); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
