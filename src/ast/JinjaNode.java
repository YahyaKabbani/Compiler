package ast;

public abstract class JinjaNode extends TemplateNode {
    private final String raw;

    protected JinjaNode(String nodeName, String raw, int line) {
        super(nodeName, line);
        this.raw = raw == null ? "" : raw.trim();
    }

    public String getRaw() { return raw; }

    @Override
    public String describe() { return raw; }
}
