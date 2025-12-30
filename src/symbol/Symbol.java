package symbol;

public class Symbol {

    public final String name;
    public final SymbolKind kind;
    public final int line;
    public final Scope scope;

    public Symbol(String name, SymbolKind kind, int line, Scope scope) {
        this.name = name;
        this.kind = kind;
        this.line = line;
        this.scope = scope;
    }

    @Override
    public String toString() {
        return kind + " '" + name + "' @line " + line;
    }
}
