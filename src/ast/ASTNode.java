package ast;

import visitor.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

public abstract class ASTNode {
    private final String nodeName;
    private final int line;

    public ASTNode(String nodeName, int line) {
        this.nodeName = nodeName;
        this.line = line;
    }

    public int getLine() { return line; }

    public String getNodeName() { return nodeName; }

    public abstract String label();

    public List<ASTNode> children() { return List.of(); }

    public String describe() { return nodeName; }

    public String opensBlock() { return null; }

    public String closesBlock() { return null; }

    public void setBody(List<ASTNode> body) { }

    public boolean isDataValue() { return false; }

    public ASTNode lookup(String key) { return null; }

    public ASTNode elementType() { return null; }

    public String asVariableName() { return null; }

    public String asCallableName() { return null; }

    public String calledFunctionName() { return null; }

    public String keywordName() { return null; }

    public ASTNode keywordValue() { return this; }

    public void print(String indent) {
        System.out.println(indent + label());
        ASTPrinter.printChildren(children(), indent);
    }

    public abstract void accept(ASTVisitor visitor);

    protected String at(String text) {
        return text + " @line " + line;
    }

    protected static final class Children {
        private final List<ASTNode> items = new ArrayList<>();

        public Children add(ASTNode node) {
            if (node != null) items.add(node);
            return this;
        }

        public Children addAll(List<ASTNode> nodes) {
            if (nodes != null) for (ASTNode n : nodes) add(n);
            return this;
        }

        public List<ASTNode> build() { return items; }
    }

    protected static Children kids() { return new Children(); }
}
