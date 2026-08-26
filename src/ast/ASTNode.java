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

    public boolean isIterable() { return false; }

    public ASTNode lookup(String key) { return null; }

    public ASTNode elementType() { return null; }

    public List<String> knownKeys() { return List.of(); }

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

    public String toJson() {
        StringBuilder sb = new StringBuilder();
        json(sb);
        return sb.toString();
    }

    private void json(StringBuilder sb) {
        sb.append("{\"node\":").append(jsonString(nodeName))
          .append(",\"line\":").append(line)
          .append(",\"label\":").append(jsonString(label()));
        jsonFields(sb);
        sb.append(",\"children\":[");
        boolean first = true;
        for (ASTNode child : children()) {
            if (!first) sb.append(',');
            first = false;
            child.json(sb);
        }
        sb.append("]}");
    }

    protected void jsonFields(StringBuilder sb) { }

    protected static String jsonString(String value) {
        StringBuilder sb = new StringBuilder("\"");
        for (char c : value.toCharArray()) {
            switch (c) {
                case '"'  -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default   -> {
                    if (c < 0x20) sb.append(String.format("\\u%04x", (int) c));
                    else sb.append(c);
                }
            }
        }
        return sb.append('"').toString();
    }

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
