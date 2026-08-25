package visitor;

import ast.ASTNode;
import ast.JinjaBlockNode;
import ast.JinjaExprNode;
import ast.JinjaExtendsNode;
import ast.JinjaForNode;
import ast.python.CallNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SemanticAnalyzer extends AbstractASTVisitor {
    private final Path templateDir;
    private final List<String> errors = new ArrayList<>();
    private final Set<String> blockNames = new HashSet<>();
    private final Map<String, Integer> childBlockLines = new LinkedHashMap<>();
    private final Deque<String> liveLoopVars = new ArrayDeque<>();
    private final Set<String> everSeenLoopVars = new HashSet<>();
    private String currentFile;
    private ASTNode currentRecord;
    private String currentLoopVar;
    private String parentTemplate;
    private Map<String, ASTNode> allTemplates;

    public SemanticAnalyzer(Path templateDir) {
        this.templateDir = templateDir;
    }

    public void analyzePython(ASTNode root, String file) {
        currentFile = file;
        if (root != null) root.accept(this);
    }

    public void analyzeTemplate(ASTNode root, String file, Map<String, ASTNode> allTemplates) {
        currentFile = file;
        blockNames.clear();
        childBlockLines.clear();
        liveLoopVars.clear();
        everSeenLoopVars.clear();
        currentRecord = null;
        currentLoopVar = null;
        parentTemplate = null;
        this.allTemplates = allTemplates;
        if (root != null) root.accept(this);
        checkOrphanBlocks();
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
        parentTemplate = parent;
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
        childBlockLines.putIfAbsent(node.getBlockName(), node.getLine());
        visitChildren(node);
    }

    @Override
    public void visit(JinjaForNode node) {
        ASTNode previousRecord = currentRecord;
        String previousVar = currentLoopVar;
        ASTNode data = node.getResolvedData();
        currentRecord = data != null ? data.elementType() : null;
        currentLoopVar = node.getVariable();

        liveLoopVars.push(node.getVariable());
        everSeenLoopVars.add(node.getVariable());
        visitChildren(node);
        liveLoopVars.pop();

        currentRecord = previousRecord;
        currentLoopVar = previousVar;
    }

    @Override
    public void visit(JinjaExprNode node) {
        if (node.getResolvedValue() == null) {
            String base = node.getBase();
            if (everSeenLoopVars.contains(base) && !liveLoopVars.contains(base)) {
                error("LOOP_VAR_OUT_OF_SCOPE", currentFile, node.getLine(),
                        "{{ " + node.getExpression() + " }} uses loop variable '" + base
                                + "' outside its {% for %} block");
            } else {
                String suggestion = base.equals(currentLoopVar)
                        ? suggestAttribute(currentRecord, node.getPath())
                        : null;
                if (suggestion != null) {
                    error("UNKNOWN_ATTRIBUTE", currentFile, node.getLine(),
                            "{{ " + node.getExpression() + " }} has no such attribute — did you mean '"
                                    + suggestion + "'?");
                } else {
                    error("UNDEFINED_VARIABLE", currentFile, node.getLine(),
                            "{{ " + node.getExpression() + " }} cannot be resolved from the data passed by render_template");
                }
            }
        }
        visitChildren(node);
    }

    private void checkOrphanBlocks() {
        if (parentTemplate == null || allTemplates == null) return;
        ASTNode parentRoot = allTemplates.get(parentTemplate);
        if (parentRoot == null) return;

        Set<String> parentBlocks = collectBlockNames(parentRoot);
        for (Map.Entry<String, Integer> entry : childBlockLines.entrySet()) {
            if (!parentBlocks.contains(entry.getKey())) {
                error("ORPHAN_BLOCK", currentFile, entry.getValue(),
                        "{% block " + entry.getKey() + " %} has no matching block in parent '"
                                + parentTemplate + "' — this override is silently dropped");
            }
        }
    }

    private static Set<String> collectBlockNames(ASTNode templateRoot) {
        BlockNameCollector collector = new BlockNameCollector();
        templateRoot.accept(collector);
        return collector.names;
    }

    private static final class BlockNameCollector extends AbstractASTVisitor {
        private final Set<String> names = new HashSet<>();

        @Override
        public void visit(JinjaBlockNode node) {
            names.add(node.getBlockName());
            visitChildren(node);
        }
    }

    private static String suggestAttribute(ASTNode value, List<String> path) {
        for (String key : path) {
            if (value == null) return null;
            ASTNode next = value.lookup(key);
            if (next == null) return closestKey(value.knownKeys(), key);
            value = next;
        }
        return null;
    }

    private static String closestKey(List<String> keys, String target) {
        String best = null;
        int bestDistance = Integer.MAX_VALUE;
        for (String key : keys) {
            int distance = levenshtein(key, target);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = key;
            }
        }
        return best != null && bestDistance <= Math.max(2, target.length() / 2) ? best : null;
    }

    private static int levenshtein(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }
        return dp[a.length()][b.length()];
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
