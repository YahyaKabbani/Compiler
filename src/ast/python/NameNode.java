package ast.python;

import ast.ASTNode;

public class NameNode extends ASTNode {

    private final String name;

    public NameNode(String name, int line) {
        super("Name", line);
        this.name = name;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + nodeName + "(" + name + ") @line " + line);
    }

    public String getName() {
        return name;
    }


}
