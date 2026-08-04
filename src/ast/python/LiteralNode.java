package ast.python;

import ast.ASTNode;

public class LiteralNode extends ASTNode {

    public enum LiteralType { STRING, NUMBER, BOOLEAN, NONE }

    private final String value;
    private final LiteralType type;

    public LiteralNode(String value, int line) {
        super("Literal", line);
        this.value = value;
        this.type = inferType(value);
    }

    private static LiteralType inferType(String value) {
        if (value.equals("True") || value.equals("False")) return LiteralType.BOOLEAN;
        if (value.equals("None"))                          return LiteralType.NONE;
        if (value.startsWith("\"") || value.startsWith("'")) return LiteralType.STRING;
        return LiteralType.NUMBER;
    }

    public String getValue() { return value; }
    public LiteralType getType() { return type; }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }

    @Override
    public void print(String indent) {
        System.out.println(indent + getNodeName() + "[" + type + "](" + value + ") @line " + getLine());
    }
}
