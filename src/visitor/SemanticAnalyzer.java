package visitor;

import ast.ASTNode;
import ast.JinjaBlockNode;
import ast.JinjaExprNode;
import ast.JinjaExtendsNode;
import ast.python.CallNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SemanticAnalyzer extends AbstractASTVisitor {
    private final Path templateDir;
    private final List<String> errors = new ArrayList<>();
    private final Set<String> blockNames = new HashSet<>();
    private String currentFile;

    public SemanticAnalyzer(Path templateDir) {
        this.templateDir = templateDir;
    }

    public void analyzePython(ASTNode root, String file) {
        currentFile = file;
        if (root != null) root.accept(this);
    }

    public void analyzeTemplate(ASTNode root, String file) {
        currentFile = file;
        blockNames.clear();
        if (root != null) root.accept(this);
    }

    public void reportUnclosed(String file, List<ASTNode> openers) {
        for (ASTNode opener : openers) {
            error("UNCLOSED_BLOCK", file, opener.getLine(),
                    "{% " + opener.opensBlock() + " %} is opened but never closed");
        }
    }

    @Override
    public void visit(CallNode node) {
        if ("render_template".equals(node.calledFunctionName()) && !node.getArgs().isEmpty()) {
            String template = unquote(node.getArgs().get(0).describe());
            if (!Files.exists(templateDir.resolve(template))) {
                error("MISSING_TEMPLATE", currentFile, node.getLine(),
                        "render_template(\"" + template + "\") but the template file does not exist");
            }
        }
        visitChildren(node);
    }

    @Override
    public void visit(JinjaExtendsNode node) {
        String parent = node.getParentTemplate();
        if (!Files.exists(templateDir.resolve(parent))) {
            error("MISSING_PARENT", currentFile, node.getLine(),
                    "{% extends \"" + parent + "\" %} but the parent template does not exist");
        }
        visitChildren(node);
    }

    @Override
    public void visit(JinjaBlockNode node) {
        if (!blockNames.add(node.getBlockName())) {
            error("DUPLICATE_BLOCK", currentFile, node.getLine(),
                    "{% block " + node.getBlockName() + " %} is defined more than once in this template");
        }
        visitChildren(node);
    }

    @Override
    public void visit(JinjaExprNode node) {
        if (node.getResolvedValue() == null) {
            error("UNDEFINED_VARIABLE", currentFile, node.getLine(),
                    "{{ " + node.getExpression() + " }} cannot be resolved from the data passed by render_template");
        }
        visitChildren(node);
    }

    private void error(String kind, String file, int line, String message) {
        errors.add(String.format("[%s] %s @line %d — %s", kind, file, line, message));
    }

    public boolean hasErrors() { return !errors.isEmpty(); }

    public List<String> getErrors() { return errors; }

    public void writeReport(Path reportFile) throws IOException {
        StringBuilder report = new StringBuilder();
        report.append("SEMANTIC ANALYSIS REPORT\n");
        report.append("========================\n");
        if (errors.isEmpty()) {
            report.append("No semantic errors found.\n");
        } else {
            report.append(errors.size()).append(" semantic error(s) found:\n\n");
            for (String e : errors) report.append(e).append('\n');
        }

        Files.createDirectories(reportFile.getParent());
        Files.writeString(reportFile, report.toString());
        System.out.print(report);
    }

    private static String unquote(String s) {
        return s == null ? null : s.replaceAll("^\"|\"$|^'|'$", "");
    }
}
