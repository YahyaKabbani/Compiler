package ast;

public class JinjaCommentNode extends JinjaNode {
    public JinjaCommentNode(String raw, int line) {
        super("JinjaComment", raw, line);
    }

    @Override
    public String label() { return at("JinjaComment " + getRaw()); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
