# Yahya — Changes Log

Work done on top of khaled's commit `1416e21`, covering **requirement 2 (AST)** and
**requirement 3 (Node structure / OOP)** from `compilerProjectDescription.pdf`.

**Scale:** 30 files modified, 20 added, 2 deleted.

---

## Starting point

khaled's commit built the skeleton and it was kept:
`ASTVisitor`, `accept()` on every node, `ContextExtractor`, `JinjaContextLinker`,
`KeywordArgumentNode`, `LiteralType`, decorator support, and `SymbolTableBuilder`
rewritten as a visitor.

The problem was that **the visitor pattern was in place but nothing was flowing through
it** — the tree had the right shape and the wrong contents. Everything below fixes that.

---

## Requirement 2 — Building the ASTs

### The data array never reached the second tree

`dictLiteral` and `listLiteral` existed in the grammar, but `FlaskPythonASTBuilder`
never overrode them, so both collapsed to `null`. There were no `ListNode` / `DictNode`
classes at all.

| Source | Before | After |
|---|---|---|
| `products.append({...})` | `Call` with **zero args** | `Dict{5 entries}` with all keys/values |
| `methods=["GET", "POST"]` | `KeywordArg` with **no value** | `List[2 elements]` with both literals |
| `request.files["photo"]` | `BinaryOp '['` (wrong node, no getter) | `Subscript request.files["photo"]` |
| `from flask import Flask, …` | dropped to `null` | `FromImport 'flask' [Flask, render_template, …]` |

**Added:** `ListNode`, `DictNode`, `DictEntryNode`, `SubscriptNode`, `ImportNode`
**Added to builder:** `visitListLiteral`, `visitDictLiteral`, `visitDictEntry`,
`visitImportStmt`, `visitFromImportStmt`, and subscript routing in `visitExpr`
**Added:** `BinaryOpNode.getOperator()` — the operator was stored but unreadable

### The generator passed a label, not data

`ContextExtractor` produced `Map<String, String>`, so `[source: read(...)]` was just
a string. It now produces `Map<String, ASTNode>` and resolves each value to its real
subtree through three hops:

```
render_template("index.html", products=read())
   → read()          resolve the call to its function definition
   → return PRODUCTS  resolve the return value
   → PRODUCTS = [{...}, {...}]   a real ListNode
```

It also binds Python `for` loop variables, so `for p in read(): … product=p` resolves
`p` to one element of the array — this is what makes `details.html` link.

### Linking only reached `for` loops

`JinjaContextLinker` handled `HtmlDocumentNode`, `ForNode` and `HtmlTagNode` and
nothing else, so every `{{ ... }}` expression stayed unresolved and `details.html`
linked nothing at all. It now walks as a visitor with a **scope stack**: entering a
loop binds the loop variable to an element of the resolved array, so expressions
inside the loop body resolve too.

```
{% for p in products %}   → JinjaFor 'p' in 'products' [source: [2 elements]]
  {{ p.name }}            → JinjaExpr {{ p.name }} → "Keyboard"
  {{ p.price }}           → JinjaExpr {{ p.price }} → 25

details.html:
  {{ product.name }}      → "Keyboard"      (was completely unresolved)
```

### Block folding only saw the top level

`ForLoopTransformer` iterated `doc.getChildren()` only. `index.html` worked purely by
luck — its `{% for %}` happened to sit at the document root. Three bugs:

1. a `{% for %}` inside a `<div>` was silently left as a flat string node
2. nested `for`-in-`for` broke — the inner `{% endfor %}` closed the outer loop
3. `{% if %}`, `{% block %}`, `{% extends %}` were never structured at all

Replaced by `JinjaBlockBuilder`: a visitor that recurses into tags and folds with a
**depth stack**, so nesting is correct. Verified on:

```html
<div class="wrap">
    {% for p in products %}
    <span>{{ p.name }}</span>
    {% for t in p.tags %}
    <em>{{ t }}</em>
    {% endfor %}
    {% endfor %}
</div>
{% if user %}<p>hi {{ user.name }}</p>{% endif %}
```

Both loops nest correctly and the `{% if %}` becomes a `JinjaIfNode` with a body.

---

## Requirement 3 — OOP: Inheritance and Polymorphism

### Inheritance was one level flat

All 20 nodes extended `ASTNode` directly. Now three levels:

```
ASTNode
├── PythonNode    → PythonProgram, FunctionDef, Assignment, Return, Call, BinaryOp,
│                    Attribute, Subscript, If, For, Name, Literal, KeywordArg,
│                    List, Dict, DictEntry, Import
├── TemplateNode  → HtmlDocument, HtmlTag, Text
│   └── JinjaNode → JinjaExpr, JinjaComment, JinjaFor, JinjaEndFor, JinjaIf,
│                   JinjaEndIf, JinjaBlock, JinjaEndBlock, JinjaExtends, JinjaRawStmt
└── CssNode       → CssStylesheet, CssRuleSet, CssDeclaration
```

A second axis was added in the visitor layer: `AbstractASTVisitor` walks children by
default, so each visitor overrides only the nodes it cares about instead of restating
30 methods.

### `JinjaNode` was a string-discriminated god class

It carried a `String type` of `"EXPR"`/`"STMT"`/`"COMMENT"` and detected a loop with
`content.startsWith("{% for")` — exactly the procedural style requirement 3 forbids.
It is now `abstract` with 10 subclasses, and classification happens once at build time
in `HTMLASTBuilder`.

### The `instanceof` chains were still there

khaled's CHANGELOG says `instanceof` was removed, but it was removed from one file and
left in three others:

- `ForLoopTransformer` — static, `instanceof`-based → now `JinjaBlockBuilder`, a visitor
- `JinjaContextLinker` — static, `instanceof`-based → now a visitor
- `ContextExtractor.resolveName()` / `resolveValue()` — `instanceof` chains inside the
  very class advertised as instanceof-free → replaced by polymorphic methods on `ASTNode`

Instead of asking *"what type is this node?"*, the code now asks the node what it can
do — `describe()`, `lookup()`, `elementType()`, `opensBlock()`, `closesBlock()`,
`asVariableName()`, `calledFunctionName()`, `keywordName()`.

**Result:** zero `instanceof` in `src/ast` and `src/visitor`.

### Node info and printing

Line numbers were stored correctly but not *printed* by `PythonProgramNode`,
`AssignmentNode`, `CallNode`, `AttributeNode`, `BinaryOpNode`, `JinjaNode`, `TextNode`
and `ForNode`. `HtmlTagNode` stored its attributes but never showed them.

`print()` is now implemented **once** in `ASTNode`; each node supplies `label()` and
`children()`, and `ASTPrinter` draws the tree. `ASTPrinter.printChildren()` was dead
code and was also broken (it double-indented) — it is fixed and is now what renders
every tree.

```
HtmlDocument @line 1
├── JinjaExtends 'base.html' @line 1
└── JinjaBlock 'content' @line 1
    ├── <h1> @line 2
    │   └── Text "Products" @line 2
    └── JinjaFor 'p' in 'products' [source: [2 elements]] @line 4
        └── <img src="/static/uploads/{{ p.photo }}" width="150"> @line 6
```

### Name collision

`ast.ForNode` collided with `ast.python.ForNode`, forcing fully-qualified overloads in
`ASTVisitor`. Renamed to `ast.JinjaForNode`.

---

## Other changes

**`Main`** no longer runs one hardcoded file. It builds the Python AST once, then loops
over `index.html`, `add.html`, `details.html`, `base.html` and `test.css`, linking each
template against the extracted context.

**`app.py`** gains a `PRODUCTS` literal array — the data array requirement 2 asks to be
passed into the second tree. `read()` falls back to it when `products.json` is absent,
and `home()` still calls `render_template("index.html", products=read())`, so the Flask
routes behave exactly as before.

**`SymbolKind`** gains `JINJA_BLOCK`, `JINJA_TEMPLATE`, `PYTHON_IMPORT`.

---

## Files

**Added (20)**
`ast/TemplateNode`, `ast/CssNode`, `ast/python/PythonNode`,
`ast/JinjaExprNode`, `ast/JinjaCommentNode`, `ast/JinjaForNode`, `ast/JinjaEndForNode`,
`ast/JinjaIfNode`, `ast/JinjaEndIfNode`, `ast/JinjaBlockNode`, `ast/JinjaEndBlockNode`,
`ast/JinjaExtendsNode`, `ast/JinjaRawStmtNode`,
`ast/python/ListNode`, `ast/python/DictNode`, `ast/python/DictEntryNode`,
`ast/python/SubscriptNode`, `ast/python/ImportNode`,
`visitor/AbstractASTVisitor`, `visitor/JinjaBlockBuilder`

**Deleted (2)**
`ast/ForNode` → replaced by `ast/JinjaForNode`
`visitor/ForLoopTransformer` → replaced by `visitor/JinjaBlockBuilder`

**Modified (30)** — all AST nodes, `ASTNode`, `ASTPrinter`, `ASTVisitor`,
`ContextExtractor`, `JinjaContextLinker`, `SymbolTableBuilder`, `HTMLASTBuilder`,
`FlaskPythonASTBuilder`, `SymbolKind`, `Main`, `app.py`

---

## How to verify

The project JDK is JBR 21 — the code uses pattern-matching syntax that JDK 11 rejects.

```bash
"D:/Applications/Installed/IntelliJ IDEA Community Edition 2025.2.4/jbr/bin/javac.exe" -encoding UTF-8 -cp dependencies/antlr-4.13.2-complete.jar -d out/production $(find src -name "*.java")
```

Run `Main` and check:

1. Python AST shows `PRODUCTS` as a `ListNode` of `DictNode`s with real values
2. `products.append({...})` and `methods=["GET","POST"]` are no longer empty
3. `index.html` — `JinjaFor 'p' in 'products' [source: [2 elements]]`
4. `{{ p.name }} → "Keyboard"` inside the loop
5. `details.html` — `{{ product.name }} → "Keyboard"`
6. `{% if %}` / `{% block %}` are structured nodes with bodies
7. every printed line ends with `@line N`; `<img>` shows its attributes
8. `grep -rn "instanceof" src/ast src/visitor` returns nothing

---

## Follow-up: alignment with the instructor's generation-stage notes

### Multi-line list literals now parse (grammar fix)

The instructor's example input is a multi-line array:

```python
products = [
    {"name": "Phone", "price": 300},
    {"name": "Laptop", "price": 800}
]
```

This **failed to parse** — 3 syntax errors, an empty `List[0 elements]`, and two dicts
orphaned at the top level. `listLiteral` had no `NEWLINE` handling while `dictLiteral` did.

Both rules now share a `layout` rule that skips `NEWLINE`/`INDENT`/`DEDENT` inside brackets,
matching Python's implicit line joining. `FlaskPythonParser` was regenerated
(into `src/gen/` and `gen/`). The instructor's example now parses with 0 errors, and
`PRODUCTS` in `app.py` is formatted normally across several lines.

### Project layout moved to the specified input contract

| Before | After |
|---|---|
| `index.html`, `add.html`, `details.html`, `base.html` at project root | `templates/index.jinja`, `add_product.jinja`, `product_details.jinja`, `base.jinja` |
| `test.css` at project root | `static/style.css` |

`app.py`'s `render_template(...)` calls and the `{% extends %}` references were updated to
match. This also fixes a real bug: Flask looks for templates in `templates/` and static files
in `static/`, so the app could not previously have served any page, and `base.html` already
referenced `/static/style.css` — a file that did not exist.

`Main` now discovers `templates/*.jinja` from the directory instead of using a hardcoded list.

### Confirmed already correct

The instructor states the symbol table must **not** be used during generation. `ContextExtractor`
and `JinjaContextLinker` never touch `SymbolTable` — the separation is already clean.

### Still outstanding

Recorded in `CLAUDE.md`: semantic analysis (requirement 4), code generation with template
inheritance / full loop iteration / Jinja-in-attributes (requirement 5), the `output/` and
`compiler_output/` writers, and the delete-product interface (requirement 6).
