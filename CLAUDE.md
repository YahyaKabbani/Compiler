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
| 4 | Semantic analysis — at least 5 semantic errors handled | **done — 11 checks** (`SemanticAnalyzer` + `JinjaBlockBuilder` unclosed reporting → `compiler_output/semantic_report.txt`) |
| 5 | Code generation — the generated parts must work together | **done** — `output/` pages generated, linked and navigable |
| 6 | Web interfaces: list products, add product, product details, delete product + smooth navigation | **done** — all four routes + navigation |
| 7 | Print each node and its children readably; print the whole tree with the symbol table | done |

Grading note: groups are differentiated by **the quality and number of semantic errors handled**
(requirement 4). 11 checks are implemented: the 5 baseline ones plus the 6 harder ones
(`UNKNOWN_ATTRIBUTE`, `LOOP_VAR_OUT_OF_SCOPE`, `ORPHAN_BLOCK`, `NOT_ITERABLE`,
`USED_BEFORE_ASSIGNMENT`, `DUPLICATE_ROUTE`) documented in `semanticChecksPlan.md`, which also
explains how to demo each with a broken-input fixture.

---

## Input / output contract

### Inputs
- `app.py` — Flask backend
- `templates/*.jinja` — `index.jinja`, `add_product.jinja`, `product_details.jinja`, `base.jinja`
- `static/style.css`, and optionally `script.js`

### Outputs

```
output/
  index.html               generated
  add_product.html         generated
  product_details.html     generated (first product)
  product_details_1.html   generated, one page per product
  product_details_2.html
  app.py                   copied as-is
  style.css                copied as-is
  static/style.css         copied so /static/... resolves when output/ is served
  script.js                copied as-is when present

compiler_output/
  ast_python.json
  ast_jinja.json
  semantic_report.txt
  generation_log.txt
```

Templates link to Flask routes (`/add`, `/product/1`), which do not exist as files in a static
folder. `RouteExtractor` reads `@app.route` + `render_template` pairs out of the Python AST, and
the generator rewrites `href`/`action` to the generated file names (`/product/2` →
`product_details_2.html`). One page is emitted per element of a route-backed collection, so every
link in `output/` resolves — requirement 6's "smooth navigation".

Serve it, do not open with `file://` — the `/static/...` paths need a web root:

```bash
cd output && python -m http.server 8123
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
  `elementType()`, `knownKeys()`, `isDataValue()`, `isIterable()`, `opensBlock()`,
  `closesBlock()`, `setBody()`, `asVariableName()`, `asCallableName()`,
  `calledFunctionName()`, `keywordName()`, `keywordValue()`. Add to this protocol rather than
  reaching for a type test.
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
"C:/Program Files/JetBrains/IntelliJ IDEA Community Edition 2025.2.6/jbr/bin/javac.exe" -encoding UTF-8 -cp dependencies/antlr-4.13.2-complete.jar -d out/production $(find src -name "*.java")
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

### 1. `script.js`
Mentioned as an optional input that must be copied to `output/`. Not present in the repo.
`OutputWriter.copySupportFiles()` already copies it when it exists.

---

## Code generation — how it works (requirement 5, done)

Built as a two-person split; `codeGenerationPlan.md` has the original task breakdown.
Yahya did Part A (tree preparation), Yazan did Part B (emission and output).

The pipeline in `Main.generate()`:

1. `RouteExtractor` pulls `@app.route(...)` → `render_template(...)` pairs from the Python AST.
2. `planPages()` decides what to emit: one page per template, plus one page per element for a
   template reached by a dynamic route (`/product/<int:id>` → `product_details_1.html`, …).
   `ContextExtractor.getCollections()` supplies the backing list for that.
3. `TemplateInheritanceResolver.resolve()` merges `{% extends %}` / `{% block %}` so a child
   template becomes one complete page. **It mutates the trees it is given**, so `Main` re-parses
   the template set for every page — otherwise two children of one base overwrite each other.
4. `HtmlGenerator` walks the merged tree and emits HTML, resolving variables with its own scope
   stack (it deliberately ignores `JinjaExprNode.getResolvedValue()`, which is bound to the
   *first* list element for checking purposes only) and rewriting `href`/`action` to file names.
5. `OutputWriter` / `CompilerOutputWriter` write `output/` and `compiler_output/`.

Dynamic HTML attributes are real nodes: `HTMLASTBuilder` splits `src="/x/{{ p.photo }}"` into
`TextNode` + `JinjaExprNode` inside an `HtmlAttributeNode`. Only *dynamic* attributes appear in
`HtmlTagNode.children()`; static ones stay in `label()` alone. Because they are children, the
inherited traversal reaches them, so `JinjaContextLinker` and `SemanticAnalyzer` handle attribute
expressions with no code of their own.

**Gotcha:** anything that visits `HtmlTagNode` must iterate `getChildren()` (elements only), not
`children()` (attributes + elements), or attribute values get processed twice. `HtmlGenerator`
does this correctly — copy it if you write another emitting visitor.

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
