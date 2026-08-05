package ast.python;

import ast.ASTNode;

import java.util.List;

public class AttributeNode extends PythonNode {
    private final ASTNode object;
    private final String attribute;

    public AttributeNode(ASTNode object, String attribute, int line) {
        super("Attribute", line);
        this.object = object;
        this.attribute = attribute;
    }

    public ASTNode getObject() { return object; }
    public String getAttribute() { return attribute; }

    @Override
    public String label() { return at("Attribute '." + attribute + "'"); }

    @Override
    public List<ASTNode> children() { return kids().add(object).build(); }

    @Override
    public String describe() {
        return (object != null ? object.describe() : "?") + "." + attribute;
    }

    @Override
    public String asCallableName() { return attribute; }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
