package ast;

import java.util.ArrayList;
import java.util.List;

public class JinjaExprNode extends JinjaNode {
    private final String expression;
    private final String base;
    private final List<String> path;

    private ASTNode resolvedValue;

    public JinjaExprNode(String raw, int line) {
        super("JinjaExpr", raw, line);

        String body = raw.replace("{{", "").replace("}}", "").trim();
        int pipe = body.indexOf('|');
        if (pipe >= 0) body = body.substring(0, pipe).trim();

        this.expression = body;

        String[] parts = body.split("\\.");
        this.base = parts.length > 0 ? parts[0].trim() : body;
        this.path = new ArrayList<>();
        for (int i = 1; i < parts.length; i++) path.add(parts[i].trim());
    }

    public String getExpression() { return expression; }
    public String getBase() { return base; }
    public List<String> getPath() { return path; }

    public ASTNode getResolvedValue() { return resolvedValue; }
    public void setResolvedValue(ASTNode value) { this.resolvedValue = value; }

    @Override
    public String label() {
        String resolved = resolvedValue != null
                ? " → " + resolvedValue.describe()
                : " → unresolved";
        return at("JinjaExpr {{ " + expression + " }}" + resolved);
    }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
