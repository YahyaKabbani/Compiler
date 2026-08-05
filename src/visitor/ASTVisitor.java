package visitor;

import ast.*;
import ast.python.*;

public interface ASTVisitor {
    void visit(PythonProgramNode node);
    void visit(FunctionDefNode node);
    void visit(AssignmentNode node);
    void visit(ReturnNode node);
    void visit(CallNode node);
    void visit(BinaryOpNode node);
    void visit(AttributeNode node);
    void visit(SubscriptNode node);
    void visit(IfNode node);
    void visit(ForNode node);
    void visit(NameNode node);
    void visit(LiteralNode node);
    void visit(KeywordArgumentNode node);
    void visit(ListNode node);
    void visit(DictNode node);
    void visit(DictEntryNode node);
    void visit(ImportNode node);

    void visit(HtmlDocumentNode node);
    void visit(HtmlTagNode node);
    void visit(HtmlAttributeNode node);
    void visit(TextNode node);

    void visit(JinjaExprNode node);
    void visit(JinjaCommentNode node);
    void visit(JinjaForNode node);
    void visit(JinjaEndForNode node);
    void visit(JinjaIfNode node);
    void visit(JinjaEndIfNode node);
    void visit(JinjaBlockNode node);
    void visit(JinjaEndBlockNode node);
    void visit(JinjaExtendsNode node);
    void visit(JinjaRawStmtNode node);

    void visit(CssStylesheetNode node);
    void visit(CssRuleSetNode node);
    void visit(CssDeclarationNode node);
}
