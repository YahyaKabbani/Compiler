package ast.python;

import ast.ASTNode;

import java.util.List;

public class KeywordArgumentNode extends PythonNode {
    private final String name;
    private final ASTNode value;

    public KeywordArgumentNode(String name, ASTNode value, int line) {
        super("KeywordArg", line);
        this.name = name;
        this.value = value;
    }

    public String getName() { return name; }
    public ASTNode getValue() { return value; }

    @Override
    public String label() { return at("KeywordArg '" + name + "'"); }

    @Override
    public List<ASTNode> children() { return kids().add(value).build(); }

    @Override
    public String describe() {
        return value != null ? value.describe() : "None";
    }

    @Override
    public String keywordName() { return name; }

    @Override
    public ASTNode keywordValue() { return value; }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
