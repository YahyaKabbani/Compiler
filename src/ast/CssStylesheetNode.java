package ast;

import java.util.List;

public class CssStylesheetNode extends CssNode {
    private final List<ASTNode> children;

    public CssStylesheetNode(List<ASTNode> children, int line) {
        super("CssStylesheet", line);
        this.children = children;
    }

    public List<ASTNode> getChildren() { return children; }

    @Override
    public String label() { return at("CssStylesheet"); }

    @Override
    public List<ASTNode> children() { return kids().addAll(children).build(); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
