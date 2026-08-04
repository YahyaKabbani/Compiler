package ast;
import visitor.ASTVisitor;

public abstract class ASTNode {
    private final String nodeName;
    private final int line;

    public ASTNode(String nodeName, int line) {
        this.nodeName = nodeName;
        this.line = line;
    }
    public int getLine() { return line; }
    public String getNodeName() { return nodeName; }
    public abstract void print(String indent);
    public abstract void accept(ASTVisitor visitor);
}