package ast.python;

import ast.ASTNode;

public class AttributeNode extends ASTNode {

    private final ASTNode object;
    private final String attribute;

    public AttributeNode(ASTNode object, String attribute, int line) {
        super("Attribute", line);
        this.object = object;
        this.attribute = attribute;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "Attribute");
        object.print(indent + "  ");
        System.out.println(indent + "  ." + attribute);
    }

    public ASTNode getObject() {
        return object;
    }

    public String getAttribute() {
        return attribute;
    }
}
