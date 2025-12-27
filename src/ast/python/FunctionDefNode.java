package ast.python;

import ast.ASTNode;
import java.util.List;

public class FunctionDefNode extends ASTNode {

    private final String name;
    private final List<String> params;
    private final List<ASTNode> body;
    private final List<ASTNode> decorators;

    public FunctionDefNode(
            String name,
            List<String> params,
            List<ASTNode> body,
            List<ASTNode> decorators,
            int line
    ) {
        super("FunctionDef", line);
        this.name = name;
        this.params = params;
        this.body = body;
        this.decorators = decorators;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + " " + name + " @line " + line);

        if (!decorators.isEmpty()) {
            System.out.println(indent + "  Decorators:");
            for (ASTNode d : decorators) {
                d.print(indent + "    ");
            }
        }

        System.out.println(indent + "  Params: " + params);
        System.out.println(indent + "  Body:");
        for (ASTNode stmt : body) {
            stmt.print(indent + "    ");
        }
    }
}
