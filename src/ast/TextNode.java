package ast;

public class TextNode extends ASTNode {

    public String text;

    public TextNode(String text, int line) {
        super("Text", line);
        this.text = text.trim();
    }

    @Override
    public void print(String indent) {
        if (!text.isEmpty()) {
            System.out.println(indent + "TEXT: " + text);
        }
    }
}
