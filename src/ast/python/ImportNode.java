package ast.python;

import java.util.List;

public class ImportNode extends PythonNode {
    private final String module;
    private final List<String> names;

    public ImportNode(String module, List<String> names, int line) {
        super(module == null ? "Import" : "FromImport", line);
        this.module = module;
        this.names = names;
    }

    public String getModule() { return module; }
    public List<String> getNames() { return names; }

    public boolean isFromImport() { return module != null; }

    @Override
    public String label() {
        return module == null
                ? at("Import " + names)
                : at("FromImport '" + module + "' " + names);
    }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
