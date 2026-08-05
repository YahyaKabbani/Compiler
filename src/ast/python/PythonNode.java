package ast.python;

import ast.ASTNode;

public abstract class PythonNode extends ASTNode {
    public PythonNode(String nodeName, int line) {
        super(nodeName, line);
    }
}
