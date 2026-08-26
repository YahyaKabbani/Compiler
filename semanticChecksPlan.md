# Semantic analysis — 6 new checks (Yahya / Yazan split)

Extends `src/visitor/SemanticAnalyzer.java`, which currently implements 5 checks
(`MISSING_TEMPLATE`, `MISSING_PARENT`, `DUPLICATE_BLOCK`, `UNDEFINED_VARIABLE`,
`UNCLOSED_BLOCK`). Those five are all one-line existence/uniqueness checks — cheap to
implement, cheap to grade. These 6 are meant to look and *be* harder: real control-flow
tracking, cross-template correlation, and a new AST-protocol method, not just
`Files.exists()` and `HashSet.add()`.

## The split

3 tiers, 1 pair per tier, both items in a pair are matched for difficulty. Do not swap
across tiers — the pairing is what makes it fair.

| Tier | Theme | Yahya | Yazan |
|---|---|---|---|
| 1 | Local value check, resolved data already on the node | 1. Attribute-typo suggestion | 2. Non-iterable `{% for %}` source |
| 2 | Analyzer must track its own ordered/scoped state | 3. Loop variable used outside its `{% for %}` | 4. Python name used before assignment |
| 3 | Cross-artifact correlation, needs more than one AST at once | 5. Orphan block in a child template | 6. Conflicting Flask routes |

## Status

Yahya's 3 (#1, #3, #5) are implemented on this branch, compiled, and verified — real
`app.py` + `templates/` still produce `No semantic errors found.`, and all three fire
correctly against an injected-bug fixture (typo'd attribute, stray loop-var reference
after `{% endfor %}`, mismatched child block name), reverted after confirming.

Yazan's 3 (#2, #4, #6 — `NOT_ITERABLE`, `USED_BEFORE_ASSIGNMENT`, `DUPLICATE_ROUTE`) are
implemented and verified the same way. The untouched repo still reports
`No semantic errors found.`; an injected-bug `app.py` (a module-level dict passed as
`products=` to `index.jinja`, a function reading `count` before `count = 0`, an exact
duplicate of `@app.route("/product/<int:id>")`, and a `/product/<name>` shape conflict)
produces, in one run:

```
[USED_BEFORE_ASSIGNMENT] app.py @line 27 — name 'count' is read before any assignment gives it a value
[DUPLICATE_ROUTE] app.py @line 76 — @app.route("/product/<int:id>") is already registered by 'product' at line 69 — Flask refuses to start when one path is claimed twice
[DUPLICATE_ROUTE] app.py @line 81 — @app.route("/product/<name>") matches the same URL shape as "/product/<int:id>" registered by 'product' at line 69 — the two routes conflict
[NOT_ITERABLE] index.jinja @line 4 — {% for p in products %} — 'products' resolves to {1 entries}, not a list — cannot iterate
```

plus the expected `UNDEFINED_VARIABLE` cascade inside the now-dead loop body (with
`products` a dict, `p` never binds — same root cause, not a false positive). Fixture
reverted after confirming; line numbers refer to the edited fixture copy. All 11 checks
now live in `SemanticAnalyzer`. New protocol method: `ASTNode.isIterable()` (default
false, overridden only in `ListNode`). New wiring in `Main`: one call,
`analyzer.checkRoutes(pythonAst, PYTHON_FILE)`, right after `analyzePython`. The
`USED_BEFORE_ASSIGNMENT` tracker whitelists a small set of Python builtins
(`PYTHON_BUILTINS` in `SemanticAnalyzer` — `__name__`, `open`, `len`, `print`, …); if a
future `app.py` uses a builtin not in that set, add it there.

**Breaking change for Yazan's merge:** `SemanticAnalyzer.analyzeTemplate` now takes a
third parameter —

```java
public void analyzeTemplate(ASTNode root, String file, Map<String, ASTNode> allTemplates)
```

— required by the `ORPHAN_BLOCK` check (#5), which needs the parent template's AST
available while analyzing the child. `Main.java` now builds
`Map<String, ASTNode> allTemplates = parseTemplates();` once, before the per-template
loop, and passes it into every `analyzeTemplate(...)` call. Build #4/#6 on top of this
signature, not the old two-arg one — if you're working from a pre-merge copy of
`SemanticAnalyzer.java`/`Main.java`, pull this branch first or you'll get a merge conflict
on that call site. Also new since the 5-check baseline: `ASTNode.knownKeys()` (default
empty, overridden in `DictNode`) — you shouldn't need it for #4/#6, but it's there if a
check ever needs a value's known keys without `instanceof`.

**Note on `CLAUDE.md`'s build command:** the `javac`/`java` path in `CLAUDE.md` points at
`2025.2.6`, but the actual install on this machine is
`IntelliJ IDEA Community Edition 2025.2.4`. Use the commands below (same flags, corrected
path) — check your own machine's version under `.../Installed/` if it differs again.

### How to test #1/#3/#5 yourself

```bash
"D:/Applications/Installed/IntelliJ IDEA Community Edition 2025.2.4/jbr/bin/javac.exe" -encoding UTF-8 -cp dependencies/antlr-4.13.2-complete.jar -d out/production $(find src -name "*.java")
"D:/Applications/Installed/IntelliJ IDEA Community Edition 2025.2.4/jbr/bin/java.exe" -Dstdout.encoding=UTF-8 -cp "out/production;dependencies/antlr-4.13.2-complete.jar" Main
```

1. Run the two commands above first, against the untouched repo, and check
   `compiler_output/semantic_report.txt` — must read `No semantic errors found.` This is
   the no-false-positives baseline; if a check fires here on valid input, it's wrong.
2. Temporarily edit (`Main` hardcodes `templates/` as the template dir, so edit in place
   and revert after — don't `git commit` these):
   - `templates/index.jinja`: change `{{ p.name }}` to `{{ p.nmae }}` → expect
     `UNKNOWN_ATTRIBUTE` on that line, suggesting `'name'`.
   - `templates/index.jinja`: add a line like `<p>{{ p.name }}</p>` after `{% endfor %}`
     but before `{% endblock %}` → expect `LOOP_VAR_OUT_OF_SCOPE` on that line.
   - `templates/add_product.jinja`: rename `{% block content %}` to
     `{% block sidebar %}` → expect `ORPHAN_BLOCK` on line 1 (`base.jinja` only declares
     `content`).
3. Re-run the `java` command from above and check `compiler_output/semantic_report.txt`
   again — should show exactly those 3 lines, each with the right file/line/message.
4. Revert the edits: `git checkout -- templates/index.jinja templates/add_product.jinja`,
   then re-run once more to confirm the report is back to `No semantic errors found.`

Running `Main` also regenerates `output/` and `compiler_output/*.json|*.txt` as a normal
side effect (per `CLAUDE.md`: "Any UI change... requires re-running generation") — that's
expected, not a sign anything broke.

Ground rules for all 6 (same rules the existing 5 already follow, see `CLAUDE.md`):

- No `instanceof` in `src/ast` or `src/visitor`. If you need to ask a node "what shape are
  you", add a method to the `ASTNode` protocol (default in `ASTNode`, override where it
  makes sense) instead of a type test. Tier 1 #2 below specifically needs a new protocol
  method — that's intentional, follow the same pattern as `isDataValue()`.
- Every new visitor method goes in a class that `extends AbstractASTVisitor`, never
  `implements ASTVisitor` directly.
- Follow the existing `error(kind, file, line, message)` → `errors` list pattern in
  `SemanticAnalyzer`. Give your check its own `KIND` string (all caps, like the existing
  ones) so it's identifiable in `compiler_output/semantic_report.txt`.
- No comments in the code — matches the rest of the repo.
- The report currently says `No semantic errors found.` for the real `app.py` +
  `templates/`. That's correct — the app is valid. To prove your check actually fires,
  you need a broken-input fixture (a scratch copy of the templates/app.py with the bug
  injected) and a screenshot or a note in your PR showing the error line it produces. A
  check that never fires in the demo is indistinguishable from a check that doesn't work.

---

## Yahya

### 1. Attribute-typo suggestion — Tier 1 — new kind: `UNKNOWN_ATTRIBUTE`

`{{ p.nmae }}` inside a `{% for p in products %}` currently just reports
`UNDEFINED_VARIABLE` — same message you'd get for a genuinely missing variable. That's not
useful: the loop variable *did* resolve, only the attribute name is wrong. Detect that case
specifically and suggest the nearest real key.

**How:** `JinjaContextLinker.visit(JinjaForNode)` already computes
`source.elementType()` (the first bound record, e.g. a `DictNode`) and pushes it into its
own scope map — but that scope is local to the linker and thrown away. Give
`SemanticAnalyzer` the same trick: override `visit(JinjaForNode)`, save the previous
"current record" in a local variable, set a field to
`node.getResolvedData() == null ? null : node.getResolvedData().elementType()`, call
`visitChildren(node)`, then restore the saved value. One field, save/restore around the
body — you don't need a full stack, this codebase never nests `{% for %}` inside
`{% for %}`.

Then in `visit(JinjaExprNode)`: if `getResolvedValue() == null` **and** the current record
is non-null **and** `node.getBase()` matches the loop variable, walk `node.getPath()`
against the record's known keys (you'll need `DictNode.getEntries()` /
`DictEntryNode.getKeyText()` — reachable via `children()` without `instanceof` since
`JinjaExprNode`/`DictEntryNode` already expose what you need through existing getters) and
find the closest key by edit distance (simple Levenshtein, no library needed). Report
`UNKNOWN_ATTRIBUTE` with a "did you mean '<closest>'?" message instead of the generic
`UNDEFINED_VARIABLE`. If nothing resolves and there's no current record, fall through to
the existing `UNDEFINED_VARIABLE` check unchanged.

**Demo fixture:** copy `templates/index.jinja`, change one `{{ p.name }}` to
`{{ p.nmae }}`.

### 2. Loop variable used outside its `{% for %}` — Tier 2 — new kind: `LOOP_VAR_OUT_OF_SCOPE`

A loop variable referenced after (or before) the `{% for %}` block that defines it should
be flagged — it has no value there. This is not what `SymbolTableBuilder` does today:
`SymbolTable`/`Scope` are only ever consulted for printing, `Scope.resolve()` walks up to
parent scopes by design (that's correct for the symbol table's own purpose), and nothing
currently checks "is this name live *right now*, at this exact point in the traversal".

**How:** give `SemanticAnalyzer` its own `Deque<String>` (or `Deque<Set<String>>` if you
want to be safe about multiple loop vars active at once) of currently-live loop variable
names. Override `visit(JinjaForNode)`: push `node.getVariable()`, `visitChildren(node)`,
pop. Override `visit(JinjaExprNode)`: if `getResolvedValue() == null` and `getBase()`
matches a name that is **not currently on the live stack but was seen as a loop variable
earlier in this template** (track a separate `Set<String> everSeenLoopVars` per
`analyzeTemplate` call, cleared alongside `blockNames`), that's the out-of-scope case —
report it with its own message instead of falling into `UNDEFINED_VARIABLE`. Order your
checks so this one runs before the generic `UNDEFINED_VARIABLE` fallback, same idea as #1.

**Demo fixture:** copy `templates/index.jinja`, add a stray `{{ p.name }}` line after the
`{% endfor %}`.

### 3. Orphan block in a child template — Tier 3 — new kind: `ORPHAN_BLOCK`

`{% block content %}` in a child template only does something if the parent
(`{% extends "base.jinja" %}`) actually declares a block with that name — otherwise
`TemplateInheritanceResolver` silently drops the override and the child's content never
appears in `output/`. Nothing today checks the child's block names against the parent's.

**How — this one needs new plumbing, not just a new `visit()`:** at the point
`analyzeTemplate` runs (`Main.java:57-75`), templates are analyzed one at a time and only
the current template's AST is in scope — there's no map of *all* parsed templates
available there (that map, `parseTemplates()`, is only built later inside `generate()`).
You need the parent's block names while analyzing the child. Cleanest fix: build the
`Map<String, ASTNode> allTemplates = parseTemplates()` (already exists as a private helper
in `Main`, may need to be made accessible or the analysis loop restructured to build it
once up front) before the loop, and pass it into a new
`SemanticAnalyzer.analyzeTemplate(ASTNode root, String file, Map<String, ASTNode> allTemplates)`
overload (or a field set once before the loop — either is fine, just don't reparse the
parent from disk a second time inside the analyzer).

Then: override `visit(JinjaExtendsNode)` to remember the parent's name for this
`analyzeTemplate` call (a field, reset like `blockNames`); collect the child's declared
block names as you already do for `DUPLICATE_BLOCK`; after the whole child tree is walked
(easiest place: end of `analyzeTemplate`, after `root.accept(this)` returns), if a parent
was found in `allTemplates`, walk the parent's tree collecting its block names the same
way, and for every child block name not present in the parent's set, report
`ORPHAN_BLOCK` at that block's line. Reuse a small local `AbstractASTVisitor` (same shape
as the `RouteCall`/`TemplateCall` helper visitors in `RouteExtractor.java` — that's the
existing pattern in this codebase for "small throwaway visitor to pull one fact out of a
tree") to collect a template's block names without duplicating traversal logic.

**Demo fixture:** copy `templates/add_product.jinja`, rename its `{% block content %}` to
`{% block sidebar %}` (base.jinja only declares `content`).

---

## Yazan

### 4. Non-iterable `{% for %}` source — Tier 1 — new kind: `NOT_ITERABLE`, plus new `ASTNode.isIterable()`

`{% for x in y %}` where `y` resolves to something that isn't actually a list — e.g.
`products` typo'd to a scalar field, or a `for` written over a dict — currently produces
no error at all; `JinjaContextLinker.visit(JinjaForNode)` just sets `resolvedData` to
whatever it found and moves on, and `SemanticAnalyzer` never even overrides
`visit(JinjaForNode)` today.

**How:** this is the one check that needs a genuinely new entry in the `ASTNode`
protocol, same pattern as `isDataValue()` (see `CLAUDE.md`: "Add to this protocol rather
than reaching for a type test"). Add `public boolean isIterable() { return false; }` to
`ASTNode`, override it to return `true` only in `ast/python/ListNode.java`. (Don't reuse
`elementType() != null` for this — an empty list legitimately has `elementType() == null`
but is still iterable, so that check would misfire on an empty products list. You need the
real yes/no signal, not an inferred one.)

Then in `SemanticAnalyzer`, override `visit(JinjaForNode)`: if `node.getResolvedData() !=
null && !node.getResolvedData().isIterable()`, report `NOT_ITERABLE` — message should
include `node.getIterable()` and `node.getResolvedData().describe()` so the report says
*what* it resolved to instead of a list (e.g. "'products' resolves to {3 entries}, not a
list — cannot iterate"). If `resolvedData` is null, that's a different, already-covered
case (undefined source) — don't double-report, let it fall through.

**Demo fixture:** in a scratch copy of `app.py`, change the `products=read()` argument
passed to `index.jinja` so it points at a single dict instead of the list (or add a second
`render_template` call feeding a dict into a `{% for %}` template).

### 5. Python name used before assignment — Tier 2 — new kind: `USED_BEFORE_ASSIGNMENT`

`SymbolTableBuilder` looks like it should already catch this but doesn't: its
`visit(NameNode)` *defines* a `PYTHON_VARIABLE` symbol for every name it sees, including
reads — so by the time you'd check `Scope.resolve()`, the read has already defined itself
in the table. `Scope.resolve()` also only ever returns the *first* symbol registered for a
name, with no line-order comparison. You can't reuse that table for this check — it needs
its own pass with its own bookkeeping.

**How:** this check almost certainly wants to live in `SemanticAnalyzer` as a self-contained
mini scope tracker, separate from `symbol.SymbolTable` (don't modify
`SymbolTableBuilder` — its job is printing the table, not enforcement, and changing its
`visit(NameNode)` behavior would break requirement 7's tree/table printing). Track,
per Python scope (module level, and one level per `FunctionDefNode` — mirror
`SymbolTableBuilder`'s `enterScope`/`exitScope` shape but with your own
`Deque<Set<String>> definedNames`): push a fresh set on `visit(FunctionDefNode)` entry
(pre-seeded with `node.getParams()`), pop on exit; add a name to the current set's top on
`visit(AssignmentNode)` and `visit(ForNode)` (the loop target) *before* visiting children so
uses inside the same statement's RHS are still checked against the prior state, add
imported names on `visit(ImportNode)`. On `visit(NameNode)`: if this name is being *read*
(you'll need to make sure you're not flagging the LHS of the assignment itself — since
`AssignmentNode`'s target is a name string, not a nested `NameNode` read, check how
`AssignmentNode`/`ForNode` expose their target vs their value children so you only walk
the value side through this check) and it is not present in the current set nor any
enclosing set nor already imported/a builtin, report `USED_BEFORE_ASSIGNMENT`.

**Demo fixture:** in a scratch copy of `app.py`, add a small function that reads a local
name before assigning it, e.g. a line using `count` before `count = 0` is set.

### 6. Conflicting Flask routes — Tier 3 — new kind: `DUPLICATE_ROUTE`

Two `@app.route(...)` decorators registering the same path is a real Flask startup error
(`AssertionError: View function mapping is overwriting an existing endpoint`), but nothing
in this codebase detects it — `RouteExtractor.visit(FunctionDefNode)` uses
`routes.putIfAbsent(route, template)`, which *silently keeps the first one and drops the
second*. That's fine for `RouteExtractor`'s actual job (feeding the generator), but it
means a real bug in `app.py` would currently generate output without any diagnostic at
all.

**How — this is cross-node, not local to one `FunctionDefNode`:** you need to see every
route in the file before you can say two of them conflict, so (like Yahya's #5) this check
naturally happens as a second pass over the whole Python AST rather than inline in a
single `visit()`. Add a method to `SemanticAnalyzer`, e.g. `checkRoutes(ASTNode
pythonAst)`, called once from `Main.java` right where `analyzer.analyzePython(...)` is
already called. Reuse the exact extraction pattern from
`RouteExtractor.RouteCall`/`routeOf()` (small nested `AbstractASTVisitor` reading
`CallNode.calledFunctionName().equals("route")`) but instead of a
`Map<String,String>` with `putIfAbsent`, collect a `Map<String, List<FunctionDefNode>>` (or
just remember the line of the first function that claimed each path) and report
`DUPLICATE_ROUTE` for every path claimed by more than one function, at the line of the
second (and any later) `@app.route`.

Go one step further for the "hard" half of this check: also catch same-shape dynamic
routes that Flask would still consider conflicting even though the literal strings differ
— `/product/<int:id>` and `/product/<id>` (or any two routes with the same number of
`/`-separated segments where every static segment matches and every dynamic segment,
regardless of its converter/name, lines up in the same position). Normalize each route by
replacing every `<...>` segment with a placeholder token before comparing, so
`/product/<int:id>` and `/product/<name>` normalize to the same
`/product/<*>` key and collide.

**Demo fixture:** in a scratch copy of `app.py`, add a second `@app.route("/product/<int:id>")`
on a throwaway function (exact duplicate), and separately a
`@app.route("/product/<name>")` alongside the real `/product/<int:id>` (shape conflict).

---

## Wiring checklist (applies to all 6)

1. New `KIND` string + message format, following `error(kind, file, line, message)` in
   `SemanticAnalyzer.java`.
2. New `visit(...)` override (or, for #5 and #6, a dedicated method) added to
   `SemanticAnalyzer`, which already `extends AbstractASTVisitor`.
3. Any new field the check needs (current record, live-loop-var stack, per-scope defined
   names, parent block names) must be **reset at the right point** — most either belong
   in `analyzeTemplate` (per-template, like the existing `blockNames.clear()`) or
   `analyzePython` (per Python-file run). Getting this wrong means state leaks across
   templates and you get either false positives or silently missing errors on the second
   template in the loop.
4. Hook any new required call into `Main.java`'s existing sequence
   (`analyzer.analyzePython(...)` / the per-template loop / `analyzer.writeReport(...)`)
   — don't create a second, separate report file.
5. Run the real `app.py` + `templates/` through it first and confirm the report still
   says "No semantic errors found" (no false positives on valid input), *then* run your
   broken-input fixture and confirm your new `KIND` shows up with the right file/line.
