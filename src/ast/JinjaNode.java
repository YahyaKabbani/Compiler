package ast;

public class JinjaNode extends ASTNode {

    private final String type;     // EXPR, STMT, COMMENT
    private final String content;  // raw text

    public JinjaNode(String type, String content, int line) {
        super("Jinja" + type, line);
        this.type = type;
        this.content = content.trim();
    }

    public boolean isForStart() {
        return type.equals("STMT") && content.startsWith("{% for");
    }

    public boolean isForEnd() {
        return type.equals("STMT") && content.startsWith("{% endfor");
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "JINJA " + type + ": " + content);
    }
}
