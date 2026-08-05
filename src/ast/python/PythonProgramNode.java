package ast.python;

import ast.ASTNode;

import java.util.List;

public class PythonProgramNode extends PythonNode {
    private final List<ASTNode> statements;

    public PythonProgramNode(List<ASTNode> statements, int line) {
        super("PythonProgram", line);
        this.statements = statements;
    }

    public List<ASTNode> getStatements() { return statements; }

    @Override
    public String label() { return at("PythonProgram"); }

    @Override
    public List<ASTNode> children() { return kids().addAll(statements).build(); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
