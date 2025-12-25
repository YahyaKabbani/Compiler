package ast;

import java.util.List;

public class HtmlTagNode extends ASTNode {

    public String tagName;
    public List<ASTNode> children;

    public HtmlTagNode(String tagName, List<ASTNode> children, int line) {
        super("HtmlTag", line);
        this.tagName = tagName;
        this.children = children;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "<" + tagName + "> (line " + line + ")");
        for (ASTNode child : children) {
            child.print(indent + "  ");
        }
    }
}
