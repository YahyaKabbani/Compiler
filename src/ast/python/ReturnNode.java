package ast.python;

import ast.ASTNode;

public class ReturnNode extends ASTNode {

    private final ASTNode value;

    public ReturnNode(ASTNode value, int line) {
        super("Return", line);
        this.value = value;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + getNodeName() + " @line " + getLine());
        value.print(indent + "  ");
    }

    public ASTNode getValue() {
        return value;
    }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
