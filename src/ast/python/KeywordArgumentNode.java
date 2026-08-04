package ast.python;

import ast.ASTNode;

public class KeywordArgumentNode extends ASTNode {

    private final String name;
    private final ASTNode value;

    public KeywordArgumentNode(String name, ASTNode value, int line) {
        super("KeywordArg", line);
        this.name = name;
        this.value = value;
    }

    public String getName()  { return name; }
    public ASTNode getValue() { return value; }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }

    @Override
    public void print(String indent) {
        System.out.println(indent + "KeywordArg " + name + " @line " + getLine());
        if (value != null) value.print(indent + "  ");
    }
}
