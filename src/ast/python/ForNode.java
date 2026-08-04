package ast.python;
import ast.ASTNode;
import java.util.List;
// ast/python/ForNode.java
public class ForNode extends ASTNode {
    private final String var;
    private final ASTNode iterable;
    private final List<ASTNode> body;

    public ForNode(String var, ASTNode iterable, List<ASTNode> body, int line) {
        super("For", line);
        this.var = var;
        this.iterable = iterable;
        this.body = body;
    }

    public String getVar() { return var; }
    public ASTNode getIterable() { return iterable; }
    public List<ASTNode> getBody() { return body; }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }

    @Override
    public void print(String indent) {
        System.out.println(indent + "For " + var + " @line " + getLine());
        iterable.print(indent + "  ");
        for (ASTNode n : body) n.print(indent + "  ");
    }
}
