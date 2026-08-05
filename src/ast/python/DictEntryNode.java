package ast.python;

import ast.ASTNode;

import java.util.List;

public class DictEntryNode extends PythonNode {
    private final ASTNode key;
    private final ASTNode value;

    public DictEntryNode(ASTNode key, ASTNode value, int line) {
        super("DictEntry", line);
        this.key = key;
        this.value = value;
    }

    public ASTNode getKey() { return key; }
    public ASTNode getValue() { return value; }

    public String getKeyText() {
        if (key == null) return "";
        return key.describe().replaceAll("^\"|\"$|^'|'$", "");
    }

    @Override
    public String label() { return at("DictEntry '" + getKeyText() + "'"); }

    @Override
    public List<ASTNode> children() { return kids().add(value).build(); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
