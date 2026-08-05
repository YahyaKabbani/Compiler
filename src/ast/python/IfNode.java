package ast.python;

import ast.ASTNode;

import java.util.List;

public class IfNode extends PythonNode {
    private final ASTNode condition;
    private final List<ASTNode> body;

    public IfNode(ASTNode condition, List<ASTNode> body, int line) {
        super("If", line);
        this.condition = condition;
        this.body = body;
    }

    public ASTNode getCondition() { return condition; }
    public List<ASTNode> getBody() { return body; }

    @Override
    public String label() { return at("If"); }

    @Override
    public List<ASTNode> children() { return kids().add(condition).addAll(body).build(); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
