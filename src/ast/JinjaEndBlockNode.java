package ast;

public class JinjaEndBlockNode extends JinjaNode {
    public JinjaEndBlockNode(String raw, int line) {
        super("JinjaEndBlock", raw, line);
    }

    @Override
    public String closesBlock() { return "block"; }

    @Override
    public String label() { return at("JinjaEndBlock"); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
