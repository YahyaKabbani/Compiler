package ast.python;

import ast.ASTNode;
import java.util.List;

public class PythonProgramNode extends ASTNode {

    private final List<ASTNode> statements;

    public PythonProgramNode(List<ASTNode> statements, int line) {
        super("PythonProgram", line);
        this.statements = statements;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "PythonProgram");
        for (ASTNode stmt : statements) {
            stmt.print(indent + "  ");
        }
    }

    public List<ASTNode> getStatements() {
        return statements;
    }
}