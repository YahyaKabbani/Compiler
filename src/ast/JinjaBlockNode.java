package ast;

import java.util.ArrayList;
import java.util.List;

public class JinjaBlockNode extends JinjaNode {
    private final String blockName;
    private List<ASTNode> body;

    public JinjaBlockNode(String blockName, List<ASTNode> body, String raw, int line) {
        super("JinjaBlock", raw, line);
        this.blockName = blockName;
        this.body = new ArrayList<>(body);
    }

    public String getBlockName() { return blockName; }
    public List<ASTNode> getBody() { return body; }

    @Override
    public String opensBlock() { return "block"; }

    @Override
    public void setBody(List<ASTNode> body) { this.body = new ArrayList<>(body); }

    @Override
    public String label() { return at("JinjaBlock '" + blockName + "'"); }

    @Override
    public List<ASTNode> children() { return kids().addAll(body).build(); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
