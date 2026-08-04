package symbol;

import java.util.*;

public class Scope {

    private final String name;
    private final Scope parent;
    private final Map<String, List<Symbol>> symbols = new HashMap<>();

    public Scope(String name, Scope parent) {
        this.name = name;
        this.parent = parent;
    }

    public void define(Symbol sym) {
        symbols.computeIfAbsent(sym.getName(), k -> new ArrayList<>()).add(sym);
    }

    public Symbol resolve(String name) {
        if (symbols.containsKey(name)) {
            return symbols.get(name).get(0);
        }
        if (parent != null) return parent.resolve(name);
        return null;
    }

    public Scope getParent() {
        return parent;
    }

    public void dump(String indent) {
        System.out.println(indent + "Scope: " + name);
        for (var list : symbols.values()) {
            for (Symbol s : list) {
                System.out.println(indent + "  " + s);
            }
        }
    }
}
