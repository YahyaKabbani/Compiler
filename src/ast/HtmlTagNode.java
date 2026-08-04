package ast;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HtmlTagNode extends ASTNode {

    private final String tagName;
    private final List<ASTNode> children;
    private final Map<String, String> attributes;

    public HtmlTagNode(String tagName, List<ASTNode> children, int line) {
        this(tagName, new LinkedHashMap<>(), children, line);
    }

    public HtmlTagNode(String tagName, Map<String, String> attributes, List<ASTNode> children, int line) {
        super("HtmlTag", line);
        this.tagName = tagName;
        this.attributes = attributes;
        this.children = children;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "<" + tagName + "> (line " + getLine() + ")");
        for (ASTNode child : children) {
            child.print(indent + "  ");
        }
    }

    public String getTagName() {
        return tagName;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
