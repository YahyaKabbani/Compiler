package ast;

import java.util.List;

public class HtmlDocumentNode extends ASTNode {

    private final List<ASTNode> children;

    public HtmlDocumentNode(List<ASTNode> children, int line) {
        super("HtmlDocument", line);
        this.children = children;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }

    @Override
    public void print(String indent) {
        System.out.println(indent + "HtmlDocument (line " + getLine() + ")");
        for (ASTNode child : children) {
            child.print(indent + "  ");
        }
    }
}
