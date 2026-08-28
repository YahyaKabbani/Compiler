package ast.python;

import ast.ASTNode;

import java.util.List;

public class ReturnNode extends PythonNode {
    private final ASTNode value;

    public ReturnNode(ASTNode value, int line) {
        super("Return", line);
        this.value = value;
    }

    public ASTNode getValue() { return value; }

    @Override
    public boolean isExitPoint() { return true; }

    @Override
    public String label() { return at("Return"); }

    @Override
    public List<ASTNode> children() { return kids().add(value).build(); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
