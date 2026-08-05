package visitor;

import ast.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class JinjaBlockBuilder extends AbstractASTVisitor {
    public static ASTNode build(ASTNode root) {
        if (root != null) root.accept(new JinjaBlockBuilder());
        return root;
    }

    @Override
    public void visit(HtmlDocumentNode node) {
        node.setChildren(fold(node.getChildren()));
        visitChildren(node);
    }

    @Override
    public void visit(HtmlTagNode node) {
        node.setChildren(fold(node.getChildren()));
        visitChildren(node);
    }

    private static final class Frame {
        final ASTNode opener;
        final String kind;
        final List<ASTNode> items = new ArrayList<>();

        Frame(ASTNode opener, String kind) {
            this.opener = opener;
            this.kind = kind;
        }
    }

    private List<ASTNode> fold(List<ASTNode> input) {
        Deque<Frame> stack = new ArrayDeque<>();
        stack.push(new Frame(null, null));

        for (ASTNode node : input) {
            String closes = node.closesBlock();
            if (closes != null && stack.size() > 1 && closes.equals(stack.peek().kind)) {
                closeTop(stack);
                continue;
            }

            String opens = node.opensBlock();
            if (opens != null) {
                stack.push(new Frame(node, opens));
                continue;
            }

            stack.peek().items.add(node);
        }

        while (stack.size() > 1) closeTop(stack);

        return stack.peek().items;
    }

    private void closeTop(Deque<Frame> stack) {
        Frame done = stack.pop();
        done.opener.setBody(done.items);
        stack.peek().items.add(done.opener);
    }
}
