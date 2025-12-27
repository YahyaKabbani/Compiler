package ast.python;
import ast.ASTNode;
import java.util.List;
// ast/python/ForNode.java
public class ForNode extends ASTNode {
    public String var;
    public ASTNode iterable;
    public List<ASTNode> body;

    public ForNode(String var, ASTNode iterable, List<ASTNode> body, int line) {
        super("For", line);
        this.var = var;
        this.iterable = iterable;
        this.body = body;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "For " + var);
        iterable.print(indent + "  ");
        for (ASTNode n : body) n.print(indent + "  ");
    }
}
