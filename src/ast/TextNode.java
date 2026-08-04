package ast;

public class TextNode extends ASTNode {

    private final String text;

    public TextNode(String text, int line) {
        super("Text", line);
        this.text = text.trim();
    }

    public String getText() { return text; }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }

    @Override
    public void print(String indent) {
        if (!text.isEmpty()) {
            System.out.println(indent + "TEXT: " + text);
        }
    }
}
