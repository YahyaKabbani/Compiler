# CLAUDE.md

Compilers course project (2025/2026). A compiler/translator for a Flask + Jinja2 + HTML + CSS
web app, written in Java with ANTLR 4.

---

## What the project must do

Two stages that work together:

1. Process the data and Python (Flask) statements.
2. Process Jinja templates and **generate HTML pages**.

The full pipeline the instructor specified:

```
app.py (Flask)
   ↓ Python Parser
Python AST
   ↓ Semantic Analysis
   ↓ Generator
Context Data  (variables and their values)
   ↓ render_template()
templates/*.jinja
   ↓ Jinja Parser
Jinja AST
   ↓ variable substitution: {{ name }} {{ age }}
HTML
   ↓
browser
```

Short version: **Python → bytecode → execution**, **Jinja → AST → HTML**.

### The 7 requirements

| # | Requirement | Status |
|---|---|---|
| 1 | Lexer & Parser + syntax for Python, Jinja2, HTML, CSS | done |
| 2 | Two ASTs (Python + Jinja2); generator passes the data array into the second tree | **done** |
| 3 | Nodes apply OOP — Inheritance + Polymorphism; every node stores its name and line | **done** |
| 4 | Semantic analysis — at least 5 semantic errors handled | **not started** |
| 5 | Code generation — the generated parts must work together | **not started** |
| 6 | Web interfaces: list products, add product, product details, delete product + smooth navigation | partial — delete missing |
| 7 | Print each node and its children readably; print the whole tree with the symbol table | done |

Grading note: groups are differentiated by **the quality and number of semantic errors handled**
(requirement 4). That is the highest-value remaining work.

---

## Input / output contract

### Inputs
- `app.py` — Flask backend
- `templates/*.jinja` — `index.jinja`, `add_product.jinja`, `product_details.jinja`, `base.jinja`
- `static/style.css`, and optionally `script.js`

### Outputs (not implemented yet)

```
output/
  index.html            generated
  add_product.html      generated
  product_details.html  generated
  app.py                copied as-is
  style.css             copied as-is
  script.js             copied as-is

compiler_output/
  ast_python.json
  ast_jinja.json
  semantic_report.txt
  generation_log.txt
```

`app.py` / `style.css` / `script.js` are **support files** — they are copied to the output
untouched, not analysed or transformed.

### Rules from the instructor
- In the generation stage, the Python data-preparation part runs first, then the values are
  passed to the Jinja template to generate the HTML pages.
- **The symbol table is NOT used during generation** — it belongs to semantic analysis and
  error checking only. (Our code already respects this: `ContextExtractor` and
  `JinjaContextLinker` never touch `SymbolTable`.)
- Any UI change (e.g. adding a product) requires re-running generation so the output matches
  the data.

---

## Architecture

```
src/
  Main.java                  drives the whole pipeline
  gen/                       ANTLR-generated (package `gen`) — do not hand-edit
  ast/
    ASTNode                  base: nodeName + line, label(), children(), accept(), print()
    ASTPrinter               draws the tree with ├── └──
    TemplateNode  ─┬─ HtmlDocumentNode, HtmlTagNode, TextNode
                   └─ JinjaNode ── JinjaExpr/Comment/For/EndFor/If/EndIf/
                                   Block/EndBlock/Extends/RawStmt
    CssNode        ── CssStylesheetNode, CssRuleSetNode, CssDeclarationNode
    python/
      PythonNode   ── PythonProgram, FunctionDef, Assignment, Return, Call, BinaryOp,
                      Attribute, Subscript, If, For, Name, Literal, KeywordArg,
                      List, Dict, DictEntry, Import
  visitor/
    ASTVisitor               one visit() per node type
    AbstractASTVisitor       default child traversal; every visitor extends this
    FlaskPythonASTBuilder    parse tree → Python AST
    HTMLASTBuilder           parse tree → template AST; classifies {% ... %} into subclasses
    CSSASTBuilder            parse tree → CSS AST
    JinjaBlockBuilder        folds {% for %}/{% if %}/{% block %} into nested nodes
    ContextExtractor         THE GENERATOR — pulls render_template data out of the Python AST
    JinjaContextLinker       binds that data onto the template AST
    SymbolTableBuilder       symbol table (semantic analysis only, never generation)
  symbol/                    Symbol, SymbolKind, Scope, SymbolTable
```

### How data reaches the template tree (requirement 2)

```
render_template("index.jinja", products=read())
   → ContextExtractor resolves read() to its FunctionDefNode
   → follows its `return PRODUCTS`
   → resolves PRODUCTS to its module-level ListNode
   → context: { "index.jinja": { "products": ListNode } }

JinjaContextLinker walks index.jinja with a scope stack:
   {% for p in products %}  → JinjaForNode.resolvedData = the full ListNode
                              binds p → first DictNode (for display/checking)
   {{ p.name }}             → JinjaExprNode.resolvedValue = Literal "Keyboard"
```

`ContextExtractor` resolves **statically** (constant folding through variables, function
returns, and Python for-loop variables) rather than executing Python. This covers the
instructor's example shape (`products = [ {...}, {...} ]` at module level) exactly.

---

## Key design rules — follow these

- **No `instanceof` in `src/ast` or `src/visitor`.** Instead of asking a node its type, ask it
  what it can do. The base `ASTNode` exposes the protocol: `describe()`, `lookup(key)`,
  `elementType()`, `isDataValue()`, `opensBlock()`, `closesBlock()`, `setBody()`,
  `asVariableName()`, `asCallableName()`, `calledFunctionName()`, `keywordName()`,
  `keywordValue()`. Add to this protocol rather than reaching for a type test.
- **Nodes never implement `print()`.** They implement `label()` (their own one line, always
  ending in `@line N`) and `children()`. `ASTNode.print()` and `ASTPrinter` do the rest.
- **Visitors extend `AbstractASTVisitor`**, never implement `ASTVisitor` directly, so adding a
  node type doesn't break every visitor.
- **The code carries no comments.** This is deliberate — do not add any.
- New node type checklist: create the class under the right parent, add `visit()` to
  `ASTVisitor` and `AbstractASTVisitor`, build it in the relevant `*ASTBuilder`.

---

## Build and run

The project JDK is **JBR 21**. `java`/`javac` on PATH is JDK 11 and will **not** compile this
code.

```bash
"D:/Applications/Installed/IntelliJ IDEA Community Edition 2025.2.4/jbr/bin/javac.exe" -encoding UTF-8 -cp dependencies/antlr-4.13.2-complete.jar -d out/production $(find src -name "*.java")
```

Run `Main` (no arguments). It builds the Python AST, runs the generator, then walks every
`templates/*.jinja` and `static/style.css`, printing each AST and symbol table.

Use `-Dstdout.encoding=UTF-8` when running from a Windows console, or the `├──` characters
render as `?`.

### Regenerating the parsers after a grammar change

**Only `src/gen/` matters.** Those files declare `package gen;` and are what the code imports
(`import gen.FlaskPythonParser`).

Regenerate by copying the `.g4` next to the output, running ANTLR there, then deleting the copy:

```bash
cd src/gen && cp ../../grammarPythonFlask/FlaskPythonParser.g4 . && java -jar ../../dependencies/antlr-4.13.2-complete.jar -Dlanguage=Java -visitor -package gen -lib . FlaskPythonParser.g4 && rm FlaskPythonParser.g4
```

**Never copy generated files into the root `gen/` directory.** It is a separate source root
holding the IntelliJ ANTLR plugin's output, and those files are in the **default package** (no
`package gen;` line). That is the only reason the two directories can coexist. Putting a
`package gen;` file there creates `gen.<Class>` in two source roots and IntelliJ fails the build
with `duplicate class: gen.<Class>`. Root `gen/` is dead weight — nothing imports it.

---

## Remaining work, in priority order

### 1. Semantic analysis (requirement 4) — highest value, this is what's graded on
Needs a `SemanticAnalyzer extends AbstractASTVisitor` producing `compiler_output/semantic_report.txt`.
Candidate checks, all detectable with what the AST already holds:
- a `{{ variable }}` used in a template that `render_template` never passes for that template
- `{% extends "x.jinja" %}` where the parent template does not exist
- `{% block %}` in a child with no matching block in the parent
- `render_template("x.jinja")` where the template file does not exist
- a Python name used before assignment / never defined (the symbol table already has scopes)
- unclosed `{% for %}` / `{% if %}` (`JinjaBlockBuilder` currently flushes these silently —
  it should report them)
- duplicate `{% block %}` names in one template
- a loop variable used outside its `{% for %}` scope

### 2. Code generation (requirement 5)
**Split between two people — see `codeGenerationPlan.md` for the full task breakdown, the
interfaces between the two parts, and the per-side definition of done. Read it before starting
any generation work.** Yahya is Part A (tree preparation), khaled is Part B (emission and output).

An `HtmlGenerator extends AbstractASTVisitor` walking the linked template AST and emitting
HTML into `output/`, plus a `generation_log.txt`. Three things must be built first:

- **Template inheritance is not resolved.** `{% extends %}` and `{% block %}` are parsed into
  nodes, but base and child are never merged. To emit a real page you must render
  `base.jinja` and substitute its `{% block content %}` with the child's block body.
- **Loops must iterate all elements.** `JinjaContextLinker` binds the loop variable to the
  *first* element only (`elementType()`), which is right for checking but not for output.
  The generator must use `JinjaForNode.getResolvedData()` — it holds the **full** `ListNode` —
  and re-bind the variable per element.
- **Jinja inside HTML attributes is still a raw string.** `<img src="/static/uploads/{{ p.photo }}">`
  keeps its value as plain text in `HtmlTagNode.attributes`, so it is never linked or
  substituted. Attribute values need to be parsed into segments (text + `JinjaExprNode`)
  before generation can fill them in.

### 3. AST JSON output
`compiler_output/ast_python.json` and `ast_jinja.json`. Cheap: add `toJson()` alongside
`label()`/`children()` on `ASTNode` and let each node contribute its own fields.

### 4. Delete-product interface (requirement 6)
`app.py` has list / add / details but no delete route, and there is no delete template.

### 5. `script.js`
Mentioned as an optional input that must be copied to `output/`. Not present in the repo.

---

## Gotchas

- `ContextExtractor` keys its context map by the **template file name string** exactly as it
  appears in `render_template(...)`. If you rename a template, update `app.py` too or the
  linking silently produces "no python context".
- `HTMLASTBuilder` drops whitespace-only text nodes. Deliberate — otherwise the tree fills
  with blank `Text` nodes.
- `Scope.resolve()` returns only the *first* symbol with a given name; scopes store a list per
  name. Worth revisiting when semantic analysis needs redefinition checks.
- `read()` in `app.py` falls back to the `PRODUCTS` literal when `products.json` is absent.
  That literal is what requirement 2 demonstrates; do not delete it.
- `CHANGELOG.md` (Arabic) documents khaled's earlier visitor refactor. `yahyaChangesLog.md`
  documents the requirement 2 + 3 work on top of it.
