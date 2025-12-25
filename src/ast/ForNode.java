package ast;

import java.util.List;

public class ForNode extends ASTNode {

    private final String variable;
    private final String iterable;
    private final List<ASTNode> body;

    public ForNode(String variable, String iterable, List<ASTNode> body, int line) {
        super("For", line);
        this.variable = variable;
        this.iterable = iterable;
        this.body = body;
    }

    public List<ASTNode> getBody() {
        return body;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "FOR " + variable + " IN " + iterable);
        for (ASTNode node : body) {
            node.print(indent + "  ");
        }
    }
}
