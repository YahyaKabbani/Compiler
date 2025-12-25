package ast;

import java.util.List;

public class CssStylesheetNode extends ASTNode {
    public final List<ASTNode> children;

    public CssStylesheetNode(List<ASTNode> children, int line) {
        super("CssStylesheet", line);
        this.children = children;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "CSS Stylesheet (line " + line + ")");
        for (ASTNode c : children) c.print(indent + "  ");
    }
}
