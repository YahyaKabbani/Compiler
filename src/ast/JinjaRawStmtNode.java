package ast;

public class JinjaRawStmtNode extends JinjaNode {
    private final String keyword;

    public JinjaRawStmtNode(String keyword, String raw, int line) {
        super("JinjaStmt", raw, line);
        this.keyword = keyword;
    }

    public String getKeyword() { return keyword; }

    @Override
    public String label() { return at("JinjaStmt '" + keyword + "' " + getRaw()); }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
