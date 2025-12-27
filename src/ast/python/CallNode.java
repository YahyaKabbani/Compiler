package ast.python;

import ast.ASTNode;
import java.util.List;

public class CallNode extends ASTNode {

    private final ASTNode target;
    private final List<ASTNode> args;

    public CallNode(ASTNode target, List<ASTNode> args, int line) {
        super("Call", line);
        this.target = target;
        this.args = args;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "Call");
        target.print(indent + "  ");
        for (ASTNode arg : args) {
            arg.print(indent + "    ");
        }
    }
}
