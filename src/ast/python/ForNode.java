package ast.python;

import ast.ASTNode;

import java.util.List;

public class ForNode extends PythonNode {
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
    public String label() { return at("For '" + var + "'"); }

    @Override
    public List<ASTNode> children() { return kids().add(iterable).addAll(body).build(); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
