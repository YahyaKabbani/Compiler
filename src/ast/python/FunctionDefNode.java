package ast.python;

import ast.ASTNode;

import java.util.List;

public class FunctionDefNode extends PythonNode {
    private final String name;
    private final List<String> params;
    private final List<ASTNode> body;
    private final List<ASTNode> decorators;

    public FunctionDefNode(
            String name,
            List<String> params,
            List<ASTNode> body,
            List<ASTNode> decorators,
            int line
    ) {
        super("FunctionDef", line);
        this.name = name;
        this.params = params;
        this.body = body;
        this.decorators = decorators;
    }

    public String getName() { return name; }
    public List<String> getParams() { return params; }
    public List<ASTNode> getBody() { return body; }
    public List<ASTNode> getDecorators() { return decorators; }

    @Override
    public String label() {
        return at("FunctionDef '" + name + "' params=" + params);
    }

    @Override
    public List<ASTNode> children() {
        return kids().addAll(decorators).addAll(body).build();
    }

    @Override
    public String describe() { return name + "(...)"; }

    @Override
    public void accept(visitor.ASTVisitor v) { v.visit(this); }
}
