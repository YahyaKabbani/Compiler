package ast.python;

import ast.ASTNode;

import java.util.List;

public class AssignmentNode extends PythonNode {
    private final String name;
    private final ASTNode value;

    public AssignmentNode(String name, ASTNode value, int line) {
        super("Assignment", line);
        this.name = name;
        this.value = value;
    }

    public String getName() { return name; }
    public ASTNode getValue() { return value; }

    @Override
    public String label() { return at("Assignment '" + name + "'"); }

    @Override
    public List<ASTNode> children() { return kids().add(value).build(); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
