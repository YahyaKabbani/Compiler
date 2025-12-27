package ast.python;
import ast.ASTNode;
import java.util.List;
// ast/python/IfNode.java
public class IfNode extends ASTNode {
    public ASTNode condition;
    public List<ASTNode> body;

    public IfNode(ASTNode condition, List<ASTNode> body, int line) {
        super("If", line);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "If");
        condition.print(indent + "  ");
        for (ASTNode n : body) n.print(indent + "  ");
    }
}
