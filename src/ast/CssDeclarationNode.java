package ast;

public class CssDeclarationNode extends ASTNode {
    public final String property;
    public final String value;

    public CssDeclarationNode(String property, String value, int line) {
        super("CssDeclaration", line);
        this.property = property;
        this.value = value;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + property + ": " + value + " (line " + line + ")");
    }
}
