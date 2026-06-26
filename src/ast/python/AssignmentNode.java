package ast.python;

import ast.ASTNode;

public class AssignmentNode extends ASTNode {

    private final String name;
    private final ASTNode value;

    public AssignmentNode(String name, ASTNode value, int line) {
        super("Assignment", line);
        this.name = name;
        this.value = value;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "Assignment " + name);
        if (value != null) value.print(indent + "  ");
    }

    public String getName() {
        return name;
    }

    public ASTNode getValue() {
        return value;
    }
}
