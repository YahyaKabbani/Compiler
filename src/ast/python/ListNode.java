package ast.python;

import ast.ASTNode;

import java.util.List;

public class ListNode extends PythonNode {
    private final List<ASTNode> elements;

    public ListNode(List<ASTNode> elements, int line) {
        super("List", line);
        this.elements = elements;
    }

    public List<ASTNode> getElements() { return elements; }

    public ASTNode firstElement() {
        return elements.isEmpty() ? null : elements.get(0);
    }

    @Override
    public ASTNode elementType() { return firstElement(); }

    @Override
    public boolean isDataValue() { return true; }

    @Override
    public String label() { return at("List[" + elements.size() + " elements]"); }

    @Override
    public List<ASTNode> children() { return kids().addAll(elements).build(); }

    @Override
    public String describe() { return "[" + elements.size() + " elements]"; }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
