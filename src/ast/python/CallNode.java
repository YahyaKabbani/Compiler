package ast.python;

import ast.ASTNode;

import java.util.List;

public class CallNode extends PythonNode {
    private final ASTNode target;
    private final List<ASTNode> args;

    public CallNode(ASTNode target, List<ASTNode> args, int line) {
        super("Call", line);
        this.target = target;
        this.args = args;
    }

    public ASTNode getTarget() { return target; }
    public List<ASTNode> getArgs() { return args; }

    @Override
    public String label() { return at("Call " + describe()); }

    @Override
    public List<ASTNode> children() { return kids().add(target).addAll(args).build(); }

    @Override
    public String describe() {
        return (target != null ? target.describe() : "?") + "(...)";
    }

    @Override
    public String calledFunctionName() {
        return target != null ? target.asCallableName() : null;
    }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
