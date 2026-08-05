package ast;

import java.util.List;

public class CssRuleSetNode extends CssNode {
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
    public String label() { return at("CssRuleSet '" + selector + "'"); }

    @Override
    public List<ASTNode> children() { return kids().addAll(declarations).build(); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
