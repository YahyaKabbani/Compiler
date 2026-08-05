# Code Generation — Work Split (Requirement 5)

Two people, two branches, one merge. **Yahya = Part A. Yazan = Part B.**

Read `CLAUDE.md` first — it holds the project flow, the architecture, and the coding rules that
apply to both parts. This file only covers who builds what and the exact interface between them.

Start this **after** semantic analysis (requirement 4) is done. The two parts here are
independent of the semantic analyzer and of each other, apart from one agreed API.

---

## Shared rules (both sides)

- Java, JDK **21** (JBR). `javac` on PATH is JDK 11 and will not compile this project.
- **No `instanceof`** anywhere in `src/ast` or `src/visitor`. Ask the node what it can do; add to
  the `ASTNode` protocol if a new question is needed.
- **No comments** in the code.
- Nodes implement `label()` and `children()`, never `print()`.
- Visitors `extend AbstractASTVisitor`, never `implements ASTVisitor`.
- Branches: `feature/codegen-a` and `feature/codegen-b`. **Merge A first**, then B rebases.

### Files each side owns — do not edit the other side's list

| Part A (Yahya) | Part B (Yazan)                                 |
|---|------------------------------------------------|
| `src/visitor/TemplateInheritanceResolver.java` *(new)* | `src/visitor/HtmlGenerator.java` *(new)*       |
| `src/ast/HtmlAttributeNode.java` *(new)* | `src/output/OutputWriter.java` *(new)*         |
| `src/ast/HtmlTagNode.java` | `src/output/CompilerOutputWriter.java` *(new)* |
| `src/visitor/HTMLASTBuilder.java` | `src/ast/ASTNode.java` (add `toJson()` only)   |
| `src/visitor/ASTVisitor.java` (add one method) | `src/Main.java`                                |
| `src/visitor/AbstractASTVisitor.java` (add one method) |                                                |

Only `Main` is a plausible conflict, and only B edits it.

---

# PART A — Tree preparation (Yahya)

Two jobs. Neither emits any HTML. Deliver **A1 first** — it unblocks B.

## A1. Template inheritance resolution

Right now `{% extends %}` and `{% block %}` are parsed into nodes but base and child stay two
separate trees. `index.jinja` alone has no `<html>`, `<head>` or `<body>` — those live in
`base.jinja`. Nothing can be emitted until they are merged.

**New file:** `src/visitor/TemplateInheritanceResolver.java`

```java
package visitor;

public final class TemplateInheritanceResolver {

    public static ASTNode resolve(String templateName, Map<String, ASTNode> templates);

    public static boolean isParentTemplate(String templateName, Map<String, ASTNode> templates);
}
```

- `templates` maps a file name exactly as written in `{% extends "..." %}` — e.g. `"base.jinja"` —
  to that template's parsed and folded `HtmlDocumentNode`.
- `resolve` returns the merged tree for `templateName`.
- **You may mutate the trees you are given.** B passes you a freshly parsed set used only for
  generation, so nothing else observes them. This is deliberate — do not deep-copy.

**Merge rules**

1. Template has no `{% extends %}` → return its tree unchanged.
2. Otherwise: take the parent's tree as the base. For every `JinjaBlockNode` in the parent, if the
   child defines a block with the same name, replace the parent block's body with the child's
   body. If the child does not define it, keep the parent's own body (that is the default content).
3. Remove the `JinjaExtendsNode` from the result.
4. Content in the child that sits outside any `{% block %}` is dropped — that is how Jinja behaves.
5. A child block with no matching parent block is dropped. Do **not** throw; the semantic analyzer
   reports it.
6. Multi-level inheritance must work (child → parent → grandparent). Recurse: resolve the parent
   first, then merge the child into the result.
7. Guard against an `{% extends %}` cycle — return the child's own tree rather than looping forever.
8. Missing parent file (not a key in `templates`) → return the child's tree unchanged. Again, no
   throw; semantic analysis reports it.

`isParentTemplate` returns true when some other template in the map extends `templateName`. B uses
it to skip emitting `base.jinja` as a page.

**Finding and replacing blocks:** walk with a small `AbstractASTVisitor` that collects
`JinjaBlockNode`s by name. Replace bodies with `JinjaBlockNode.setBody(List<ASTNode>)`, which
already exists.

**Done when:** `resolve("index.jinja", templates)` prints as one tree starting at `<html>`, with
`<head>`/`<body>`/navbar from `base.jinja`, and the products `{% for %}` sitting where
`{% block content %}` was. No `JinjaExtends` node remains.

## A2. Jinja inside HTML attributes

`<img src="/static/uploads/{{ p.photo }}" width="150" />` currently keeps its whole value as one
plain `String` in `HtmlTagNode.attributes`. The `{{ p.photo }}` is never parsed, never linked, and
therefore can never be substituted at generation time.

**New file:** `src/ast/HtmlAttributeNode.java`

```java
package ast;

public class HtmlAttributeNode extends TemplateNode {

    public HtmlAttributeNode(String name, String rawValue, List<ASTNode> valueParts, int line);

    public String getName();
    public String getRawValue();
    public List<ASTNode> getValueParts();   // TextNode and JinjaExprNode, in order
    public boolean isDynamic();             // true when any part is a JinjaExprNode

    @Override public String label();        // at("Attribute '" + name + "'")
    @Override public List<ASTNode> children();   // valueParts
    @Override public void accept(ASTVisitor v);
}
```

**Change `HtmlTagNode`:**
- keep `Map<String, String> getAttributes()` exactly as it is — `label()` and `SymbolTableBuilder`
  both rely on it, do not break them
- add `List<HtmlAttributeNode> getAttributeNodes()`
- `children()` returns **dynamic attribute nodes first, then element children**. Static attributes
  stay out of `children()`; they already show in `label()` and adding them would bury the tree in
  noise. Dynamic ones must be in `children()` so visitors reach the `JinjaExprNode` inside.

**Change `HTMLASTBuilder`:** where attributes are read in `visitHtmlElement`, split each value into
parts on `{{ ... }}` and build the `HtmlAttributeNode`. Reuse `JinjaExprNode` for each expression —
its constructor already takes the raw `{{ ... }}` text and splits base/path.

**Change `ASTVisitor` and `AbstractASTVisitor`:** add `void visit(HtmlAttributeNode node);` and the
default `visitChildren(node)` body.

**`JinjaContextLinker` needs no change.** Because the dynamic attribute nodes are in `children()`,
the inherited traversal already reaches their `JinjaExprNode`s and the existing
`visit(JinjaExprNode)` resolves them. Verify this — after your change,
`{{ p.photo }}` inside the `<img>` must print as resolved.

**Done when:**
```
<img src="/static/uploads/{{ p.photo }}" width="150"> @line 6
└── Attribute 'src' @line 6
    ├── Text "/static/uploads/" @line 6
    └── JinjaExpr {{ p.photo }} → "kb.png" @line 6
```

---

# PART B — Emission and output (Yazan)

## B1. The HTML generator

**New file:** `src/visitor/HtmlGenerator.java`

```java
package visitor;

public class HtmlGenerator extends AbstractASTVisitor {

    public static String generate(ASTNode template, Map<String, ASTNode> context);

    public List<String> getLog();
}
```

- `template` is a merged tree from `TemplateInheritanceResolver.resolve(...)`.
- `context` is one template's entry from `ContextExtractor.extract(pythonAst)` — that is
  `Map<String, ASTNode>`, variable name → its Python value subtree. May be `null` for a template
  that takes no data.
- Returns the finished HTML.

**Do your own variable resolution.** Keep a `Deque<Map<String, ASTNode>> scopes` exactly like
`JinjaContextLinker` does, seeded with `context`. Do **not** read
`JinjaExprNode.getResolvedValue()` — the linker binds the loop variable to the *first* list element
only, which is right for checking and wrong for output. Ignore it and resolve yourself, so you are
not coupled to the linker at all.

**Emission rules**

| Node | Output |
|---|---|
| `HtmlDocumentNode` | children concatenated |
| `HtmlTagNode` | `<tag attrs>` + children + `</tag>`; void elements (`br hr img input link meta`) emit no closing tag |
| `TextNode` | `getText()` as-is |
| `JinjaExprNode` | resolved value, unquoted; empty string if unresolved (and log a warning) |
| `JinjaForNode` | iterate **every** element; per element push a scope binding the loop variable, emit the body, pop |
| `JinjaIfNode` | emit body when the condition resolves truthy; skip when it resolves to `None`/`False`/empty list; emit when it cannot be resolved |
| `JinjaBlockNode` | emit its body (after A's merge this is the final content) |
| `JinjaExtends`, `JinjaComment`, `JinjaEndFor`, `JinjaEndIf`, `JinjaEndBlock`, `JinjaRawStmt` | nothing |

**Loops:** the full data is on `JinjaForNode.getResolvedData()` when the linker ran, but prefer
resolving `getIterable()` through your own scope stack. Get the elements via
`ListNode.getElements()`. Bind the loop variable to each element in turn — that element is normally
a `DictNode`, so `{{ p.name }}` then works through `DictNode.lookup("name")`.

**Expressions:** `JinjaExprNode.getBase()` gives the variable, `getPath()` the attribute chain.
Resolve the base from the scope stack, then walk the path with `ASTNode.lookup(key)`. Unwrap the
final value with `LiteralNode.getRawValue()` so you emit `Keyboard`, not `"Keyboard"`.

**Attributes — write this so it works before A lands:**

```
if (node.getAttributeNodes() is available and non-empty)
    emit each part: TextNode raw, JinjaExprNode resolved
else
    emit node.getAttributes() raw       // fallback
```

With the fallback you can build and test the entire generator against `base.jinja`, which has no
`{% extends %}` and no dynamic attributes, **before A merges anything**. Delete the fallback after
A's branch lands.

**Log** every substitution as you go:
```
[page]  index.jinja -> output/index.html (1240 bytes)
[loop]  index.jinja:4  {% for p in products %} -> 2 iterations
[expr]  index.jinja:7  {{ p.name }} -> Keyboard
[warn]  index.jinja:6  {{ p.photo }} unresolved
```

## B2. Output writers

**New file:** `src/output/OutputWriter.java`

```java
package output;

public final class OutputWriter {

    public static void writePage(String templateName, String html);  // index.jinja -> output/index.html
    public static void copySupportFiles();                           // app.py, static/style.css, static/script.js
    public static void clean();                                      // wipe output/ before a run
}
```

**New file:** `src/output/CompilerOutputWriter.java`

```java
package output;

public final class CompilerOutputWriter {

    public static void writeAstJson(String fileName, ASTNode root);  // compiler_output/<fileName>
    public static void writeGenerationLog(List<String> lines);       // compiler_output/generation_log.txt
}
```

Target layout, straight from the instructor's notes:

```
output/
  index.html            generated
  add_product.html      generated
  product_details.html  generated
  app.py                copied untouched
  style.css             copied untouched
  script.js             copied untouched, only if it exists

compiler_output/
  ast_python.json
  ast_jinja.json
  semantic_report.txt   written by the semantic analyzer, not by you
  generation_log.txt
```

`app.py` / `style.css` / `script.js` are support files — copy them byte for byte, never transform them.

## B3. AST → JSON

Add to `src/ast/ASTNode.java` only:

```java
public abstract class ASTNode {

    public String toJson();

    protected void jsonFields(StringBuilder sb);
}
```

`toJson()` builds the document; `jsonFields` is an optional hook that writes nothing by default.

Shape:

```json
{
  "node": "JinjaFor",
  "line": 4,
  "label": "JinjaFor 'p' in 'products' [source: [2 elements]] @line 4",
  "children": [
    {
      "node": "HtmlTag",
      "line": 5,
      "label": "<div class=\"product\"> @line 5",
      "children": []
    }
  ]
}
```

`node` is `getNodeName()`, `label` is `label()`, `children` is `children()` mapped recursively.
That is enough for the deliverable — resist adding per-node fields unless something needs them.
Escape `"`, `\` and newlines in strings.

`ast_jinja.json` should hold the **merged, generation-ready** template trees, keyed by template
name, so the file reflects what was actually generated.

## B4. Main wiring

```
pythonAst   = buildPythonAst("app.py")
contexts    = new ContextExtractor().extract(pythonAst)

templates   = every templates/*.jinja parsed fresh into a Map<String, ASTNode>
              (a second parse, separate from the trees you print — A mutates these)

OutputWriter.clean()
for each name in templates:
    if (TemplateInheritanceResolver.isParentTemplate(name, templates)) continue   // skip base.jinja
    merged = TemplateInheritanceResolver.resolve(name, templates)
    html   = HtmlGenerator.generate(merged, contexts.get(name))
    OutputWriter.writePage(name, html)

OutputWriter.copySupportFiles()
CompilerOutputWriter.writeAstJson("ast_python.json", pythonAst)
CompilerOutputWriter.writeAstJson("ast_jinja.json", mergedTrees)
CompilerOutputWriter.writeGenerationLog(log)
```

Keep the existing printing of ASTs and symbol tables — that is requirement 7 and still graded.

---

## Definition of done

**Part A**
- [ ] `resolve("index.jinja", ...)` yields one tree rooted at `<html>` with the loop inside the
      navbar layout, and no `JinjaExtends` left
- [ ] multi-level `{% extends %}` works; a cycle does not hang
- [ ] a dynamic attribute prints its `Text` + `JinjaExpr` parts as children, resolved
- [ ] static attributes still appear in `label()` and are absent from `children()`
- [ ] `SymbolTableBuilder` output for templates is unchanged
- [ ] compiles clean, no `instanceof`, no comments

**Part B**
- [ ] `output/index.html` opens in a browser and shows the navbar plus **both** products
- [ ] `output/product_details.html` and `output/add_product.html` generated; `base.jinja` skipped
- [ ] `app.py` and `style.css` copied into `output/`
- [ ] `compiler_output/ast_python.json`, `ast_jinja.json`, `generation_log.txt` written and valid
- [ ] the log records each loop iteration count and each substitution
- [ ] compiles clean, no `instanceof`, no comments

**After merging both**
- [ ] `output/index.html` shows two product images with correct `src` paths from `{{ p.photo }}`
- [ ] the `Details` link on each card carries the right `{{ p.id }}`
- [ ] regenerating after editing `PRODUCTS` in `app.py` changes the HTML accordingly

---

## Order of work

1. **A1** first — B is blocked on it for every template except `base.jinja`.
2. **B1 + B2 + B3 in parallel**, developed against `base.jinja` with the raw-attribute fallback.
3. **A2** second.
4. Merge A, then B rebases, removes the fallback, and verifies the combined checklist.
