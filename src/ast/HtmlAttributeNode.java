package ast;

import java.util.ArrayList;
import java.util.List;

public class HtmlAttributeNode extends TemplateNode {
    private final String name;
    private final String rawValue;
    private final List<ASTNode> valueParts;

    public HtmlAttributeNode(String name, String rawValue, List<ASTNode> valueParts, int line) {
        super("HtmlAttribute", line);
        this.name = name;
        this.rawValue = rawValue == null ? "" : rawValue;
        this.valueParts = new ArrayList<>(valueParts);
    }

    public String getName() { return name; }

    public String getRawValue() { return rawValue; }

    public List<ASTNode> getValueParts() { return valueParts; }

    public boolean isDynamic() { return rawValue.contains("{{"); }

    @Override
    public String label() { return at("HtmlAttribute '" + name + "'"); }

    @Override
    public List<ASTNode> children() { return kids().addAll(valueParts).build(); }

    @Override
    public String describe() { return name + "=" + rawValue; }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
