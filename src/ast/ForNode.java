package ast;

import java.util.List;

public class ForNode extends ASTNode {

    private final String variable;
    private final String iterable;
    private final List<ASTNode> body;
    private String resolvedSource; // مصدر البيانات من Python AST مثل "read(...)"

    public ForNode(String variable, String iterable, List<ASTNode> body, int line) {
        super("For", line);
        this.variable = variable;
        this.iterable = iterable;
        this.body = body;
    }

    public List<ASTNode> getBody()      { return body; }
    public String getVariable()         { return variable; }
    public String getIterable()         { return iterable; }
    public String getResolvedSource()   { return resolvedSource; }

    public void setResolvedSource(String source) {
        this.resolvedSource = source;
    }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }

    @Override
    public void print(String indent) {
        String src = resolvedSource != null ? " [source: " + resolvedSource + "]" : " [source: unresolved]";
        System.out.println(indent + "FOR " + variable + " IN " + iterable + src);
        for (ASTNode node : body) {
            node.print(indent + "  ");
        }
    }
}
