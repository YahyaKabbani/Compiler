package symbol;

public class Symbol {

    private final String name;
    private final SymbolKind kind;
    private final int line;
    private final Scope scope;

    public Symbol(String name, SymbolKind kind, int line, Scope scope) {
        this.name = name;
        this.kind = kind;
        this.line = line;
        this.scope = scope;
    }

    public String getName()    { return name; }
    public SymbolKind getKind() { return kind; }
    public int getLine()       { return line; }
    public Scope getScope()    { return scope; }

    @Override
    public String toString() {
        return kind + " '" + name + "' @line " + line;
    }
}
