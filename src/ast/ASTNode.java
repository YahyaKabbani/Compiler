package ast;
public abstract class ASTNode {
    protected String nodeName;
    protected int line;

    public ASTNode(String nodeName, int line) {
        this.nodeName = nodeName;
        this.line = line;
    }
    public int getLine() { return line; }
    // كل عقدة يجب أن تنفذ هذه الدالة بطريقتها
    public abstract void print(String indent);
}