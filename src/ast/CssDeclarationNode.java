package ast;

public class CssDeclarationNode extends ASTNode {
    private final String property;
    private final String value;

    public CssDeclarationNode(String property, String value, int line) {
        super("CssDeclaration", line);
        this.property = property;
        this.value = value;
    }

    public String getProperty() { return property; }
    public String getValue() { return value; }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }

    @Override
    public void print(String indent) {
        System.out.println(indent + property + ": " + value + " (line " + getLine() + ")");
    }
}
