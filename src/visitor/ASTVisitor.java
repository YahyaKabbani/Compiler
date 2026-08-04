package visitor;

import ast.*;
import ast.python.*;

public interface ASTVisitor {

    // Python
    void visit(PythonProgramNode node);
    void visit(FunctionDefNode node);
    void visit(AssignmentNode node);
    void visit(ReturnNode node);
    void visit(CallNode node);
    void visit(BinaryOpNode node);
    void visit(AttributeNode node);
    void visit(IfNode node);
    void visit(ast.python.ForNode node);
    void visit(NameNode node);
    void visit(LiteralNode node);
    void visit(KeywordArgumentNode node);

    // HTML
    void visit(HtmlDocumentNode node);
    void visit(HtmlTagNode node);
    void visit(JinjaNode node);
    void visit(TextNode node);
    void visit(ast.ForNode node);

    // CSS
    void visit(CssStylesheetNode node);
    void visit(CssRuleSetNode node);
    void visit(CssDeclarationNode node);
}
