package ast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HtmlTagNode extends TemplateNode {
    private final String tagName;
    private final Map<String, String> attributes;
    private final List<HtmlAttributeNode> attributeNodes;
    private List<ASTNode> children;
    private String endTag;
    private boolean selfClosing;

    public HtmlTagNode(String tagName, List<ASTNode> children, int line) {
        this(tagName, new LinkedHashMap<>(), children, line);
    }

    public HtmlTagNode(String tagName, Map<String, String> attributes, List<ASTNode> children, int line) {
        super("HtmlTag", line);
        this.tagName = tagName;
        this.attributes = attributes;
        this.attributeNodes = new ArrayList<>();
        this.children = new ArrayList<>(children);
    }

    private HtmlTagNode(String tagName, List<HtmlAttributeNode> attributeNodes, int line, List<ASTNode> children) {
        super("HtmlTag", line);
        this.tagName = tagName;
        this.attributeNodes = new ArrayList<>(attributeNodes);
        this.attributes = new LinkedHashMap<>();
        for (HtmlAttributeNode attribute : this.attributeNodes) {
            this.attributes.put(attribute.getName(), attribute.getRawValue());
        }
        this.children = new ArrayList<>(children);
    }

    public static HtmlTagNode withAttributes(String tagName, List<HtmlAttributeNode> attributeNodes,
                                             List<ASTNode> children, int line) {
        return new HtmlTagNode(tagName, attributeNodes, line, children);
    }

    public String getTagName() { return tagName; }

    public String getEndTag() { return endTag; }

    public void setEndTag(String endTag) { this.endTag = endTag; }

    public void setSelfClosing() { this.selfClosing = true; }

    public boolean isSelfClosing() { return selfClosing; }

    public List<String> classNames() {
        List<String> names = new ArrayList<>();
        String value = null;
        for (Map.Entry<String, String> a : attributes.entrySet()) {
            if ("class".equalsIgnoreCase(a.getKey())) {
                value = a.getValue();
                break;
            }
        }
        if (value == null || value.contains("{{")) return names;
        String clean = value.replaceAll("^[\"']+|[\"']+$", "");
        for (String token : clean.split("\\s+")) {
            if (!token.isEmpty() && !names.contains(token)) names.add(token);
        }
        return names;
    }

    public Map<String, String> getAttributes() { return attributes; }

    public List<HtmlAttributeNode> getAttributeNodes() { return attributeNodes; }

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
    public List<ASTNode> children() {
        Children kids = kids();
        for (HtmlAttributeNode attribute : attributeNodes) {
            if (attribute.isDynamic()) kids.add(attribute);
        }
        return kids.addAll(children).build();
    }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
