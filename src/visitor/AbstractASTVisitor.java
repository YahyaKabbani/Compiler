package visitor;

import ast.*;
import ast.python.*;

public abstract class AbstractASTVisitor implements ASTVisitor {
    protected void visitChildren(ASTNode node) {
        if (node == null) return;
        for (ASTNode child : node.children()) child.accept(this);
    }

    @Override public void visit(PythonProgramNode node)   { visitChildren(node); }
    @Override public void visit(FunctionDefNode node)      { visitChildren(node); }
    @Override public void visit(AssignmentNode node)       { visitChildren(node); }
    @Override public void visit(ReturnNode node)           { visitChildren(node); }
    @Override public void visit(CallNode node)             { visitChildren(node); }
    @Override public void visit(BinaryOpNode node)         { visitChildren(node); }
    @Override public void visit(AttributeNode node)        { visitChildren(node); }
    @Override public void visit(SubscriptNode node)        { visitChildren(node); }
    @Override public void visit(IfNode node)               { visitChildren(node); }
    @Override public void visit(ForNode node)              { visitChildren(node); }
    @Override public void visit(NameNode node)             { visitChildren(node); }
    @Override public void visit(LiteralNode node)          { visitChildren(node); }
    @Override public void visit(KeywordArgumentNode node)  { visitChildren(node); }
    @Override public void visit(ListNode node)             { visitChildren(node); }
    @Override public void visit(DictNode node)             { visitChildren(node); }
    @Override public void visit(DictEntryNode node)        { visitChildren(node); }
    @Override public void visit(ImportNode node)           { visitChildren(node); }

    @Override public void visit(HtmlDocumentNode node)     { visitChildren(node); }
    @Override public void visit(HtmlTagNode node)          { visitChildren(node); }
    @Override public void visit(HtmlAttributeNode node)    { visitChildren(node); }
    @Override public void visit(TextNode node)             { visitChildren(node); }

    @Override public void visit(JinjaExprNode node)        { visitChildren(node); }
    @Override public void visit(JinjaCommentNode node)     { visitChildren(node); }
    @Override public void visit(JinjaForNode node)         { visitChildren(node); }
    @Override public void visit(JinjaEndForNode node)      { visitChildren(node); }
    @Override public void visit(JinjaIfNode node)          { visitChildren(node); }
    @Override public void visit(JinjaEndIfNode node)       { visitChildren(node); }
    @Override public void visit(JinjaBlockNode node)       { visitChildren(node); }
    @Override public void visit(JinjaEndBlockNode node)    { visitChildren(node); }
    @Override public void visit(JinjaExtendsNode node)     { visitChildren(node); }
    @Override public void visit(JinjaRawStmtNode node)     { visitChildren(node); }

    @Override public void visit(CssStylesheetNode node)    { visitChildren(node); }
    @Override public void visit(CssRuleSetNode node)       { visitChildren(node); }
    @Override public void visit(CssDeclarationNode node)   { visitChildren(node); }
}
