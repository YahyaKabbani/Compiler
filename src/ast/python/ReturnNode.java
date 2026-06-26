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
        System.out.println(indent + nodeName + " @line " + line);
        value.print(indent + "  ");
    }

    public ASTNode getValue() {
        return value;
    }
}
