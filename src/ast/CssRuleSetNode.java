package ast;

import java.util.List;

public class CssRuleSetNode extends ASTNode {
    public final String selector;
    public final List<ASTNode> declarations;

    public CssRuleSetNode(String selector, List<ASTNode> declarations, int line) {
        super("CssRuleSet", line);
        this.selector = selector;
        this.declarations = declarations;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "RULE: " + selector + " (line " + line + ")");
        for (ASTNode d : declarations) d.print(indent + "  ");
    }
}
