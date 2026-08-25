package ast.python;

import ast.ASTNode;

import java.util.ArrayList;
import java.util.List;

public class DictNode extends PythonNode {
    private final List<DictEntryNode> entries;

    public DictNode(List<DictEntryNode> entries, int line) {
        super("Dict", line);
        this.entries = entries;
    }

    public List<DictEntryNode> getEntries() { return entries; }

    @Override
    public ASTNode lookup(String key) {
        for (DictEntryNode e : entries) {
            if (key.equals(e.getKeyText())) return e.getValue();
        }
        return null;
    }

    @Override
    public List<String> knownKeys() {
        List<String> keys = new ArrayList<>();
        for (DictEntryNode e : entries) keys.add(e.getKeyText());
        return keys;
    }

    @Override
    public boolean isDataValue() { return true; }

    @Override
    public String label() { return at("Dict{" + entries.size() + " entries}"); }

    @Override
    public List<ASTNode> children() {
        Children c = kids();
        for (DictEntryNode e : entries) c.add(e);
        return c.build();
    }

    @Override
    public String describe() { return "{" + entries.size() + " entries}"; }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
