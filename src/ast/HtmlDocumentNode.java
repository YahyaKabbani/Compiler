package ast;

import java.util.ArrayList;
import java.util.List;

public class HtmlDocumentNode extends TemplateNode {
    private List<ASTNode> children;

    public HtmlDocumentNode(List<ASTNode> children, int line) {
        super("HtmlDocument", line);
        this.children = new ArrayList<>(children);
    }

    public List<ASTNode> getChildren() { return children; }

    public void setChildren(List<ASTNode> children) {
        this.children = new ArrayList<>(children);
    }

    @Override
    public String label() { return at("HtmlDocument"); }

    @Override
    public List<ASTNode> children() { return kids().addAll(children).build(); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
