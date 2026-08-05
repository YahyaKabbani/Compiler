package ast;

import java.util.ArrayList;
import java.util.List;

public class JinjaForNode extends JinjaNode {
    private final String variable;
    private final String iterable;
    private List<ASTNode> body;

    private String resolvedSource;
    private ASTNode resolvedData;

    public JinjaForNode(String variable, String iterable, List<ASTNode> body, String raw, int line) {
        super("JinjaFor", raw, line);
        this.variable = variable;
        this.iterable = iterable;
        this.body = new ArrayList<>(body);
    }

    public String getVariable() { return variable; }
    public String getIterable() { return iterable; }
    public List<ASTNode> getBody() { return body; }

    public String getResolvedSource() { return resolvedSource; }
    public void setResolvedSource(String source) { this.resolvedSource = source; }

    public ASTNode getResolvedData() { return resolvedData; }
    public void setResolvedData(ASTNode data) { this.resolvedData = data; }

    @Override
    public String opensBlock() { return "for"; }

    @Override
    public void setBody(List<ASTNode> body) { this.body = new ArrayList<>(body); }

    @Override
    public String label() {
        String src = resolvedSource != null ? resolvedSource : "unresolved";
        return at("JinjaFor '" + variable + "' in '" + iterable + "' [source: " + src + "]");
    }

    @Override
    public List<ASTNode> children() { return kids().addAll(body).build(); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
