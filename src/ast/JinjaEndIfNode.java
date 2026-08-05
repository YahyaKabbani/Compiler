package ast;

public class JinjaEndIfNode extends JinjaNode {
    public JinjaEndIfNode(String raw, int line) {
        super("JinjaEndIf", raw, line);
    }

    @Override
    public String closesBlock() { return "if"; }

    @Override
    public String label() { return at("JinjaEndIf"); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
