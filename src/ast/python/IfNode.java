package ast.python;
import ast.ASTNode;
import java.util.List;
// ast/python/IfNode.java
public class IfNode extends ASTNode {
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
    public void accept(visitor.ASTVisitor v) { v.visit(this); }

    @Override
    public void print(String indent) {
        System.out.println(indent + "If @line " + getLine());
        condition.print(indent + "  ");
        for (ASTNode n : body) n.print(indent + "  ");
    }
}
