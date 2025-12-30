package symbol;
import java.util.List;
import java.util.ArrayList;

public class SymbolTable {

    private final List<Scope> allScopes = new ArrayList<>();
    private Scope current;

    public void enterScope(String name) {
        current = new Scope(name, current);
        allScopes.add(current);
    }


    public void exitScope() {
        if (current != null) current = current.getParent();
    }

    public Scope currentScope() {
        return current;
    }

    public void define(Symbol sym) {
        current.define(sym);
    }

    public void dump() {
        Scope s = current;
        while (s != null) {
            s.dump("");
            s = s.getParent();
        }
    }

    public void dumpAll() {
        for (Scope s : allScopes) {
            s.dump("");
            System.out.println();
        }
    }

}
