package ast.python;

import ast.ASTNode;

import java.util.List;

public class SubscriptNode extends PythonNode {
    private final ASTNode target;
    private final ASTNode index;

    public SubscriptNode(ASTNode target, ASTNode index, int line) {
        super("Subscript", line);
        this.target = target;
        this.index = index;
    }

    public ASTNode getTarget() { return target; }
    public ASTNode getIndex() { return index; }

    @Override
    public String label() { return at("Subscript " + describe()); }

    @Override
    public List<ASTNode> children() { return kids().add(target).add(index).build(); }

    @Override
    public String describe() {
        String t = target != null ? target.describe() : "?";
        String i = index != null ? index.describe() : "?";
        return t + "[" + i + "]";
    }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
