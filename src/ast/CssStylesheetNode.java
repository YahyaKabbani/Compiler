package ast;

import java.util.List;

public class CssStylesheetNode extends ASTNode {
    private final List<ASTNode> children;

    public CssStylesheetNode(List<ASTNode> children, int line) {
        super("CssStylesheet", line);
        this.children = children;
    }

    public List<ASTNode> getChildren() { return children; }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }

    @Override
    public void print(String indent) {
        System.out.println(indent + "CSS Stylesheet (line " + getLine() + ")");
        for (ASTNode c : children) c.print(indent + "  ");
    }
}
