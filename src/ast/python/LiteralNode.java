package ast.python;

import ast.ASTNode;

public class LiteralNode extends ASTNode {

    private final String value;

    public LiteralNode(String value, int line) {
        super("Literal", line);
        this.value = value;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + "(" + value + ") @line " + line);
    }
}
