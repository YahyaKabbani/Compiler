package ast;

public class TextNode extends TemplateNode {
    private final String text;

    public TextNode(String text, int line) {
        super("Text", line);
        this.text = text == null ? "" : text.trim();
    }

    private TextNode(int line, String exactText) {
        super("Text", line);
        this.text = exactText == null ? "" : exactText;
    }

    public static TextNode raw(String text, int line) {
        return new TextNode(line, text);
    }

    public String getText() { return text; }

    public boolean isBlank() { return text.isEmpty(); }

    @Override
    public String label() { return at("Text \"" + text + "\""); }

    @Override
    public String describe() { return text; }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
