package ast;

public class JinjaExtendsNode extends JinjaNode {
    private final String parentTemplate;

    public JinjaExtendsNode(String parentTemplate, String raw, int line) {
        super("JinjaExtends", raw, line);
        this.parentTemplate = parentTemplate;
    }

    public String getParentTemplate() { return parentTemplate; }

    @Override
    public String label() { return at("JinjaExtends '" + parentTemplate + "'"); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
