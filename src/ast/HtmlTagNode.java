package ast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HtmlTagNode extends TemplateNode {
    private final String tagName;
    private final Map<String, String> attributes;
    private List<ASTNode> children;

    public HtmlTagNode(String tagName, List<ASTNode> children, int line) {
        this(tagName, new LinkedHashMap<>(), children, line);
    }

    public HtmlTagNode(String tagName, Map<String, String> attributes, List<ASTNode> children, int line) {
        super("HtmlTag", line);
        this.tagName = tagName;
        this.attributes = attributes;
        this.children = new ArrayList<>(children);
    }

    public String getTagName() { return tagName; }

    public Map<String, String> getAttributes() { return attributes; }

    public List<ASTNode> getChildren() { return children; }

    public void setChildren(List<ASTNode> children) {
        this.children = new ArrayList<>(children);
    }

    @Override
    public String label() {
        StringBuilder sb = new StringBuilder("<").append(tagName);
        for (Map.Entry<String, String> a : attributes.entrySet()) {
            sb.append(' ').append(a.getKey()).append("=").append(a.getValue());
        }
        sb.append('>');
        return at(sb.toString());
    }

    @Override
    public List<ASTNode> children() { return kids().addAll(children).build(); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
