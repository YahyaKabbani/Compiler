# Semantic Analysis — Test Cases

The analyzer is `src/visitor/SemanticAnalyzer.java` (plus unclosed-block reporting in
`src/visitor/JinjaBlockBuilder.java`). It runs automatically at the end of `Main` and writes
its report to `compiler_output/semantic_report.txt`.

All test inputs live in **`semantic_tests/`**. Each file contains a comment at the top saying
what it breaks and the exact error expected. The clean project reports **0 errors** — every
error below only appears when its test file is put in place.

## The 5 checks and their test cases

| # | Error code | Meaning | Test file |
|---|---|---|---|
| 1 | `MISSING_TEMPLATE` | `render_template("x.jinja")` but the template file does not exist | `semantic_tests/test1_missing_template.py` |
| 2 | `MISSING_PARENT` | `{% extends "x.jinja" %}` but the parent template does not exist | `semantic_tests/test2_missing_parent.jinja` |
| 3 | `DUPLICATE_BLOCK` | the same `{% block %}` name defined twice in one template | `semantic_tests/test3_duplicate_block.jinja` |
| 4 | `UNCLOSED_BLOCK` | `{% for %}` / `{% if %}` / `{% block %}` opened but never closed | `semantic_tests/test4_unclosed_for.jinja` |
| 5 | `UNDEFINED_VARIABLE` | a `{{ variable }}` that `render_template` never passes to that template | `semantic_tests/test5_undefined_variable.jinja` |

## How to run the template tests (2–5)

`Main` analyses every `.jinja` file inside `templates/`, so just copy the test files in:

```
copy semantic_tests\test2_missing_parent.jinja templates\
copy semantic_tests\test3_duplicate_block.jinja templates\
copy semantic_tests\test4_unclosed_for.jinja templates\
copy semantic_tests\test5_undefined_variable.jinja templates\
```

Run `Main`, then check the report. Clean up afterwards:

```
del templates\test2_missing_parent.jinja templates\test3_duplicate_block.jinja templates\test4_unclosed_for.jinja templates\test5_undefined_variable.jinja
```

## How to run the Python test (1)

Test 1 is a full replacement `app.py` whose `home()` renders `"indexx.jinja"` (typo):

```
copy app.py app_backup.py
copy semantic_tests\test1_missing_template.py app.py
```

Run `Main`, check the report, then restore:

```
copy app_backup.py app.py
del app_backup.py
```

## Verified output (all 5 tests in place)

```
[MISSING_TEMPLATE] app.py @line 31 — render_template("indexx.jinja") but the template file does not exist
[MISSING_PARENT] test2_missing_parent.jinja @line 3 — {% extends "base_layout.jinja" %} but the parent template does not exist
[DUPLICATE_BLOCK] test3_duplicate_block.jinja @line 7 — {% block content %} is defined more than once in this template
[UNCLOSED_BLOCK] test4_unclosed_for.jinja @line 6 — {% for %} is opened but never closed
[UNCLOSED_BLOCK] test4_unclosed_for.jinja @line 4 — {% block %} is opened but never closed
[UNDEFINED_VARIABLE] test5_undefined_variable.jinja @line 5 — {{ shop_name }} cannot be resolved from the data passed by render_template
```

Note on test 4: it reports **two** unclosed blocks. The missing `{% endfor %}` means the
`{% endblock %}` is treated as stray (it cannot close a `for`), so the outer `{% block %}` is
also left unclosed. This cascade is normal compiler behaviour — one real mistake, two
accurate reports.
