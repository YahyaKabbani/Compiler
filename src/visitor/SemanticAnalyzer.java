package visitor;

import ast.ASTNode;
import ast.CssRuleSetNode;
import ast.HtmlTagNode;
import ast.JinjaBlockNode;
import ast.JinjaExprNode;
import ast.JinjaExtendsNode;
import ast.JinjaForNode;
import ast.python.AssignmentNode;
import ast.python.CallNode;
import ast.python.ForNode;
import ast.python.FunctionDefNode;
import ast.python.ImportNode;
import ast.python.NameNode;
import ast.python.PythonProgramNode;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SemanticAnalyzer extends AbstractASTVisitor {
    private static final Set<String> PYTHON_BUILTINS = Set.of(
            "__name__", "print", "len", "open", "range", "str", "int", "float",
            "bool", "list", "dict", "set", "tuple", "input", "enumerate", "sorted",
            "sum", "min", "max", "abs", "type", "isinstance", "zip", "map", "filter");

    private static final Set<String> VOID_TAGS = Set.of(
            "br", "hr", "img", "input", "link", "meta");

    private final Path templateDir;
    private final List<String> errors = new ArrayList<>();
    private final Set<String> blockNames = new HashSet<>();
    private final Map<String, Integer> childBlockLines = new LinkedHashMap<>();
    private final Deque<String> liveLoopVars = new ArrayDeque<>();
    private final Deque<Set<String>> definedNames = new ArrayDeque<>();
    private final Set<String> everSeenLoopVars = new HashSet<>();
    private final Set<String> seenFunctions = new HashSet<>();
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
        definedNames.clear();
        seenFunctions.clear();
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
    public void visit(PythonProgramNode node) {
        definedNames.push(new HashSet<>());
        visitChildren(node);
        definedNames.pop();
    }

    @Override
    public void visit(FunctionDefNode node) {
        if (!seenFunctions.add(node.getName())) {
            error("DUPLICATE_FUNCTION", currentFile, node.getLine(),
                    "function '" + node.getName() + "' is defined more than once — the later definition silently overrides the earlier one");
        }
        define(node.getName());
        for (ASTNode decorator : node.getDecorators()) decorator.accept(this);
        definedNames.push(new HashSet<>(node.getParams()));
        boolean exited = false;
        for (ASTNode statement : node.getBody()) {
            if (exited) {
                error("UNREACHABLE_CODE", currentFile, statement.getLine(),
                        "statement after 'return' is unreachable");
                break;
            }
            statement.accept(this);
            if (statement.isExitPoint()) exited = true;
        }
        definedNames.pop();
    }

    @Override
    public void visit(AssignmentNode node) {
        visitChildren(node);
        define(node.getName());
    }

    @Override
    public void visit(ForNode node) {
        if (node.getIterable() != null) node.getIterable().accept(this);
        define(node.getVar());
        for (ASTNode statement : node.getBody()) statement.accept(this);
    }

    @Override
    public void visit(ImportNode node) {
        for (String name : node.getNames()) define(name);
        visitChildren(node);
    }

    @Override
    public void visit(NameNode node) {
        if (!definedNames.isEmpty() && !isDefined(node.getName())) {
            error("USED_BEFORE_ASSIGNMENT", currentFile, node.getLine(),
                    "name '" + node.getName() + "' is read before any assignment gives it a value");
        }
        visitChildren(node);
    }

    private void define(String name) {
        if (name != null && !definedNames.isEmpty()) definedNames.peek().add(name);
    }

    private boolean isDefined(String name) {
        if (PYTHON_BUILTINS.contains(name)) return true;
        for (Set<String> scope : definedNames) {
            if (scope.contains(name)) return true;
        }
        return false;
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
        if (data != null && !data.isIterable()) {
            error("NOT_ITERABLE", currentFile, node.getLine(),
                    "{% for " + node.getVariable() + " in " + node.getIterable() + " %} — '"
                            + node.getIterable() + "' resolves to " + data.describe()
                            + ", not a list — cannot iterate");
        }
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

    @Override
    public void visit(HtmlTagNode node) {
        String tag = node.getTagName().toLowerCase();
        if (!"__group__".equals(tag) && !VOID_TAGS.contains(tag) && !node.isSelfClosing()) {
            String endTag = node.getEndTag();
            if (endTag == null) {
                error("UNCLOSED_TAG", currentFile, node.getLine(),
                        "<" + node.getTagName() + "> is opened but never closed");
            } else if (!endTag.equalsIgnoreCase(node.getTagName())) {
                error("MISMATCHED_TAGS", currentFile, node.getLine(),
                        "<" + node.getTagName() + "> is closed by </" + endTag + "> — mismatched closing tag");
            }
        }
        visitChildren(node);
    }

    public void checkCssClasses(Map<String, ASTNode> templates, ASTNode cssRoot) {
        if (cssRoot == null) return;
        Set<String> defined = new HashSet<>();
        cssRoot.accept(new CssClassCollector(defined));
        for (Map.Entry<String, ASTNode> entry : templates.entrySet()) {
            UsedClassCollector used = new UsedClassCollector(defined);
            entry.getValue().accept(used);
            for (Map.Entry<String, Integer> v : used.violations.entrySet()) {
                error("UNDEFINED_CSS_CLASS", entry.getKey(), v.getValue(),
                        "class '" + v.getKey() + "' used in " + entry.getKey()
                                + " has no matching ." + v.getKey() + " rule in static/style.css");
            }
        }
    }

    private static final class CssClassCollector extends AbstractASTVisitor {
        private final Set<String> classes;

        CssClassCollector(Set<String> classes) {
            this.classes = classes;
        }

        @Override
        public void visit(CssRuleSetNode node) {
            Pattern p = Pattern.compile("\\.([\\w-]+)");
            Matcher m = p.matcher(node.getSelector());
            while (m.find()) classes.add(m.group(1));
            visitChildren(node);
        }
    }

    private static final class UsedClassCollector extends AbstractASTVisitor {
        private final Set<String> defined;
        private final Map<String, Integer> violations = new LinkedHashMap<>();

        UsedClassCollector(Set<String> defined) {
            this.defined = defined;
        }

        @Override
        public void visit(HtmlTagNode node) {
            for (String name : node.classNames()) {
                if (!defined.contains(name)) {
                    violations.putIfAbsent(name, node.getLine());
                }
            }
            visitChildren(node);
        }
    }

    public void checkRoutes(ASTNode pythonAst, String file) {
        if (pythonAst == null) return;
        RouteCollector collector = new RouteCollector();
        pythonAst.accept(collector);

        Map<String, RouteRegistration> claimed = new LinkedHashMap<>();
        for (RouteRegistration registration : collector.registrations) {
            RouteRegistration first = claimed.putIfAbsent(normalizeRoute(registration.path), registration);
            if (first == null) continue;
            String message = registration.path.equals(first.path)
                    ? "@app.route(\"" + registration.path + "\") is already registered by '"
                            + first.function + "' at line " + first.line
                            + " — Flask refuses to start when one path is claimed twice"
                    : "@app.route(\"" + registration.path + "\") matches the same URL shape as \""
                            + first.path + "\" registered by '" + first.function + "' at line "
                            + first.line + " — the two routes conflict";
            error("DUPLICATE_ROUTE", file, registration.line, message);
        }
    }

    private static String normalizeRoute(String path) {
        return path.replaceAll("<[^>]*>", "<*>");
    }

    private static final class RouteRegistration {
        private final String path;
        private final String function;
        private final int line;

        RouteRegistration(String path, String function, int line) {
            this.path = path;
            this.function = function;
            this.line = line;
        }
    }

    private static final class RouteCollector extends AbstractASTVisitor {
        private final List<RouteRegistration> registrations = new ArrayList<>();

        @Override
        public void visit(FunctionDefNode node) {
            for (ASTNode decorator : node.getDecorators()) {
                RouteCall call = new RouteCall();
                decorator.accept(call);
                if (call.path != null) {
                    registrations.add(new RouteRegistration(call.path, node.getName(), call.line));
                }
            }
            visitChildren(node);
        }
    }

    private static final class RouteCall extends AbstractASTVisitor {
        private String path;
        private int line;

        @Override
        public void visit(CallNode node) {
            if (path == null && "route".equals(node.calledFunctionName()) && !node.getArgs().isEmpty()) {
                path = unquote(node.getArgs().get(0).describe());
                line = node.getLine();
            }
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
