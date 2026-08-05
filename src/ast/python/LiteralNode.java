package ast.python;

public class LiteralNode extends PythonNode {
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

    public String getRawValue() {
        return value.replaceAll("^\"|\"$|^'|'$", "");
    }

    @Override
    public boolean isDataValue() { return true; }

    @Override
    public String label() { return at("Literal[" + type + "](" + value + ")"); }

    @Override
    public String describe() { return value; }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
