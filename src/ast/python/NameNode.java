package ast.python;

public class NameNode extends PythonNode {
    private final String name;

    public NameNode(String name, int line) {
        super("Name", line);
        this.name = name;
    }

    public String getName() { return name; }

    @Override
    public String label() { return at("Name(" + name + ")"); }

    @Override
    public String describe() { return name; }

    @Override
    public String asVariableName() { return name; }

    @Override
    public String asCallableName() { return name; }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
