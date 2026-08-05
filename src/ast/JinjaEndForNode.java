package ast;

public class JinjaEndForNode extends JinjaNode {
    public JinjaEndForNode(String raw, int line) {
        super("JinjaEndFor", raw, line);
    }

    @Override
    public String closesBlock() { return "for"; }

    @Override
    public String label() { return at("JinjaEndFor"); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
