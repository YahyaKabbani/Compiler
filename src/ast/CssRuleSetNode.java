package ast;

import java.util.List;

public class CssRuleSetNode extends ASTNode {
    private final String selector;
    private final List<ASTNode> declarations;

    public CssRuleSetNode(String selector, List<ASTNode> declarations, int line) {
        super("CssRuleSet", line);
        this.selector = selector;
        this.declarations = declarations;
    }

    public String getSelector() { return selector; }
    public List<ASTNode> getDeclarations() { return declarations; }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }

    @Override
    public void print(String indent) {
        System.out.println(indent + "RULE: " + selector + " (line " + getLine() + ")");
        for (ASTNode d : declarations) d.print(indent + "  ");
    }
}
