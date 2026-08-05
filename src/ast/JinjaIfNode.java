package ast;

import java.util.ArrayList;
import java.util.List;

public class JinjaIfNode extends JinjaNode {
    private final String condition;
    private List<ASTNode> body;

    public JinjaIfNode(String condition, List<ASTNode> body, String raw, int line) {
        super("JinjaIf", raw, line);
        this.condition = condition;
        this.body = new ArrayList<>(body);
    }

    public String getCondition() { return condition; }
    public List<ASTNode> getBody() { return body; }

    @Override
    public String opensBlock() { return "if"; }

    @Override
    public void setBody(List<ASTNode> body) { this.body = new ArrayList<>(body); }

    @Override
    public String label() { return at("JinjaIf '" + condition + "'"); }

    @Override
    public List<ASTNode> children() { return kids().addAll(body).build(); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
