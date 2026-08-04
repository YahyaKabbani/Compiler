# مشروع المترجِل - Flask/HTML/CSS Compiler

مترجِل/محلل لغوي مكتوب بـ **Java** باستخدام **ANTLR4**، يقوم بتحليل ملفات تطبيق Flask ويبني شجرة AST وجدول الرموز لثلاث لغات: Python/Flask، HTML/Jinja2، CSS.

---

## هيكل المشروع

```
Compiler/
├── grammarPythonFlask/       # قواعد ANTLR لـ Python/Flask
│   ├── FlaskPythonLexer.g4
│   └── FlaskPythonParser.g4
├── grammarsHTML/             # قواعد ANTLR لـ HTML + Jinja2
│   ├── HTMLJinja2Lexer.g4
│   └── HTMLJinja2Parser.g4
├── grammersCSS/              # قواعد ANTLR لـ CSS
│   ├── CSSLexer.g4
│   └── CSSParser.g4
├── src/
│   ├── ast/                  # عقد شجرة AST
│   │   ├── python/           # عقد Python
│   │   └── ...               # عقد HTML و CSS
│   ├── symbol/               # جدول الرموز
│   ├── visitor/              # بناة AST وجدول الرموز
│   └── Main.java             # نقطة الدخول
├── gen/                      # ملفات ANTLR المولّدة تلقائياً
├── dependencies/
│   └── antlr-4.13.2-complete.jar
├── app.py                    # مثال Flask
├── index.html / base.html    # أمثلة HTML
└── test.css                  # مثال CSS
```

---

## مراحل المترجِل

### المرحلة 1 — التحليل اللغوي (Lexer & Parser)

تم تعريف ثلاث grammars باستخدام ANTLR4:

| Grammar | اللغة | ما يدعمه |
|---------|-------|----------|
| `FlaskPythonLexer/Parser.g4` | Python/Flask | functions, decorators, imports, if, for, assignments, expressions, dict, list |
| `HTMLJinja2Lexer/Parser.g4` | HTML + Jinja2 | tags, attributes, `{{ expr }}`, `{% stmt %}`, comments |
| `CSSLexer/Parser.g4` | CSS | selectors, declarations, media queries, keyframes, variables, calc |

> الـ Lexer الخاص بـ Python يتعامل مع **INDENT/DEDENT** يدوياً لمحاكاة سلوك Python الحقيقي.

---

### المرحلة 2 — بناء شجرة AST

كل لغة لها `ASTBuilder` خاص يرث من `BaseVisitor` المولّد بـ ANTLR:

#### عقد Python (`src/ast/python/`)

| العقدة | الوصف |
|--------|-------|
| `PythonProgramNode` | جذر البرنامج |
| `FunctionDefNode` | تعريف دالة (اسم، parameters، body، decorators) |
| `AssignmentNode` | إسناد متغير |
| `CallNode` | استدعاء دالة |
| `BinaryOpNode` | عملية ثنائية (`+`, `>`, `<`, `==`) |
| `IfNode` | جملة شرطية |
| `ForNode` | حلقة for |
| `ReturnNode` | جملة return |
| `NameNode` | اسم متغير |
| `LiteralNode` | قيمة ثابتة (string, number, True, False, None) |
| `AttributeNode` | وصول لخاصية (`obj.attr`) |

#### عقد HTML (`src/ast/`)

| العقدة | الوصف |
|--------|-------|
| `HtmlDocumentNode` | جذر المستند |
| `HtmlTagNode` | وسم HTML مع attributes وأبناء |
| `JinjaNode` | تعبير Jinja2 (EXPR / STMT / COMMENT) |
| `TextNode` | نص عادي |
| `ForNode` | حلقة Jinja2 `{% for %}` بعد التحويل |

#### عقد CSS (`src/ast/`)

| العقدة | الوصف |
|--------|-------|
| `CssStylesheetNode` | جذر ملف CSS |
| `CssRuleSetNode` | مجموعة قواعد (selector + declarations) |
| `CssDeclarationNode` | خاصية CSS (property: value) |

#### `ForLoopTransformer`
يحوّل عقد Jinja2 `{% for %}` / `{% endfor %}` المتفرقة في HTML إلى `ForNode` هرمي منظّم.

---

### المرحلة 3 — جدول الرموز (Symbol Table)

#### البنية

```
SymbolTable
└── Scope (global)
    ├── Scope (function: index)
    │   ├── Symbol: param 'name' @line 5
    │   └── ...
    ├── Scope (html: div)
    │   └── Symbol: jinja_var 'user' @line 12
    └── Scope (jinja-for)
        └── Symbol: loop_var 'item' @line 20
```

#### أنواع الرموز (`SymbolKind`)

| النوع | الوصف |
|-------|-------|
| `PYTHON_FUNCTION` | دالة Python |
| `PYTHON_VARIABLE` | متغير Python |
| `PARAMETER` | معامل دالة |
| `HTML_TAG` | وسم HTML |
| `JINJA_VARIABLE` | متغير Jinja2 `{{ var }}` |
| `JINJA_LOOP_VAR` | متغير حلقة `{% for x in ... %}` |
| `CSS_CLASS` | كلاس CSS `.name` |
| `CSS_ID` | معرّف CSS `#name` |
| `CSS_TAG` | وسم CSS `div`, `p`, ... |

---

## نقطة الدخول — `Main.java`

يقرأ الملف المحدد ويختار المحلل تلقائياً حسب الامتداد:

```java
String filePath = "app.py"; // غيّر هنا لـ .html أو .css
```

**الخطوات:**
1. قراءة الملف وإنشاء `CharStream`
2. تشغيل الـ Lexer والـ Parser المناسب
3. بناء AST عبر الـ Visitor
4. طباعة AST بشكل هرمي (`ASTPrinter`)
5. بناء جدول الرموز (`SymbolTableBuilder`)
6. طباعة جميع الـ Scopes والرموز

---

## التقنيات المستخدمة

| التقنية | الإصدار | الغرض |
|---------|---------|-------|
| Java | 17+ | لغة البرمجة الرئيسية |
| ANTLR4 | 4.13.2 | توليد Lexer/Parser |
| Visitor Pattern | — | بناء AST من Parse Tree |

---

## كيفية التشغيل

```bash
# تأكد من وجود antlr-4.13.2-complete.jar في dependencies/
# ثم شغّل Main.java من IDE أو:

javac -cp dependencies/antlr-4.13.2-complete.jar src/**/*.java
java -cp .:dependencies/antlr-4.13.2-complete.jar Main
```

> غيّر قيمة `filePath` في `Main.java` لتحليل ملف مختلف (`app.py`, `index.html`, `test.css`).

---

## تقرير حالة المشروع — طلب بطلب

---

### الطلب 1 — مراحل المترجم الأساسية (Lexer & Parser)

| المكوّن | الحالة | التفاصيل |
|---------|--------|----------|
| Python/Flask Lexer | ✅ مكتمل | keywords, operators, INDENT/DEDENT يدوي, strings, numbers, identifiers |
| Python/Flask Parser | ✅ مكتمل | functions, decorators, imports, if, for, assignments, dict, list, expressions |
| HTML/Jinja2 Lexer | ✅ مكتمل | tags, attributes, `{{ }}`, `{% %}`, `{# #}`, script, style, modes |
| HTML/Jinja2 Parser | ✅ مكتمل | htmlDocument, htmlElement, htmlContent, attributes, jinja, script, style |
| CSS Lexer | ✅ مكتمل | selectors, properties, values, media, keyframes, variables, calc |
| CSS Parser | ✅ مكتمل | ruleset, declaration, media queries, keyframes, supports, viewport, font-face |
| INDENT/DEDENT Python | ✅ مكتمل | معالجة يدوية كاملة في `@members` تحاكي سلوك Python الحقيقي |

**الحكم: ✅ مكتمل بالكامل**

---

### الطلب 2 — بناء شجرة AST

| المكوّن | الحالة | التفاصيل |
|---------|--------|----------|
| Python AST — `FlaskPythonASTBuilder` | ✅ مكتمل | يبني: FunctionDef, Assignment, Call, BinaryOp, If, For, Return, Name, Literal, Attribute |
| HTML AST — `HTMLASTBuilder` | ✅ مكتمل | يبني: HtmlDocument, HtmlTag, JinjaNode, TextNode |
| CSS AST — `CSSASTBuilder` | ✅ مكتمل | يبني: CssStylesheet, CssRuleSet, CssDeclaration |
| `ForLoopTransformer` | ✅ مكتمل | يحوّل `{% for %}` المتفرقة إلى ForNode هرمي |
| ربط Python AST بـ Jinja AST | ❌ غير موجود | لا يوجد ContextExtractor يمرر البيانات من شجرة Python إلى قوالب Jinja |

**الحكم: ⚠️ مكتمل جزئياً — الشجرتان مبنيتان لكن لا يوجد ربط بينهما**

---

### الطلب 3 — هيكلية العقد (OOP)

| النقطة | الحالة | التفاصيل |
|--------|--------|----------|
| Inheritance | ✅ مكتمل | كل عقدة ترث من `ASTNode` |
| Polymorphism — `print()` | ✅ مكتمل | كل عقدة تعيد تعريف `print()` بطريقتها الخاصة |
| تخزين اسم العقدة | ✅ مكتمل | كل عقدة تمرر اسمها عبر `super("NodeName", line)` |
| تخزين رقم السطر | ✅ مكتمل | جميع العقد تخزن `line` وتعرضه |
| Polymorphism في `SymbolTableBuilder` | ⚠️ ناقص | يستخدم `instanceof` بدل `accept(visitor)` |
| Encapsulation — حقول `private` | ⚠️ ناقص | `IfNode`, `ForNode`, `CssRuleSetNode`, `TextNode`, `CssStylesheetNode` حقولها `public` |
| `LiteralNode` — نوع القيمة | ⚠️ ناقص | لا يميّز بين String / Number / Boolean |
| `FunctionDefNode` — decorators | ⚠️ ناقص | الحقل موجود لكن دائماً `List.of()` فارغة |

**الحكم: ⚠️ مكتمل جزئياً — OOP الأساسي موجود لكن Encapsulation وPolymorphism الكامل ناقصان**

---

### الطلب 4 — التحليل الدلالي (Semantic Analysis)

| النقطة | الحالة | التفاصيل |
|--------|--------|----------|
| `SemanticAnalyzer` | ❌ غير موجود | لا يوجد أي كلاس للتحليل الدلالي |
| استخدام متغير قبل تعريفه | ❌ غير موجود | — |
| استدعاء دالة غير معرّفة | ❌ غير موجود | — |
| تعريف متغير/دالة مكررة | ❌ غير موجود | — |
| type mismatch | ❌ غير موجود | — |
| متغير Jinja غير معرّف في Python | ❌ غير موجود | — |
| `semantic_report.txt` | ❌ غير موجود | — |

**الحكم: ❌ غير موجود بالكامل — المطلوب 5 أخطاء دلالية على الأقل**

---

### الطلب 5 — توليد الكود (Code Generation)

| النقطة | الحالة | التفاصيل |
|--------|--------|----------|
| `ContextExtractor` | ❌ غير موجود | لا يوجد من يستخرج القيم الفعلية من Python AST |
| `CodeGenerator` | ❌ غير موجود | لا يوجد من يستبدل `{{ var }}` بالقيم ويولّد HTML |
| مجلد `output/` | ❌ غير موجود | لا تُولَّد أي ملفات HTML |
| مجلد `compiler_output/` | ❌ غير موجود | — |
| `ast_python.json` | ❌ غير موجود | لا يوجد تصدير AST كـ JSON |
| `ast_jinja.json` | ❌ غير موجود | — |
| `generation_log.txt` | ❌ غير موجود | — |

**الحكم: ❌ غير موجود بالكامل**

---

### الطلب 6 — الواجهات والتنقل

| النقطة | الحالة | التفاصيل |
|--------|--------|----------|
| واجهة عرض المنتجات | ✅ موجود | في مشروع Flask خارجي |
| واجهة إضافة منتج | ✅ موجود | في مشروع Flask خارجي |
| واجهة تفاصيل منتج | ✅ موجود | في مشروع Flask خارجي |
| التنقل بين الواجهات | ✅ موجود | في مشروع Flask خارجي |

**الحكم: ✅ مكتمل في مشروع Flask الخارجي**

---

### الطلب 7 — الطباعة (Print)

| النقطة | الحالة | التفاصيل |
|--------|--------|----------|
| طباعة كل عقدة وأبنائها | ✅ مكتمل | كل عقدة لها `print()` خاص بها |
| طباعة هرمية بأسلوب tree | ✅ مكتمل | `ASTPrinter` يستخدم (├── └──) |
| طباعة جدول الرموز | ✅ مكتمل | `dumpAll()` يطبع جميع الـ Scopes والرموز |

**الحكم: ✅ مكتمل بالكامل**

---

### ملخص عام

| الطلب | الحالة |
|-------|--------|
| 1 — Lexer & Parser | ✅ مكتمل |
| 2 — بناء AST | ⚠️ مكتمل جزئياً |
| 3 — هيكلية العقد OOP | ⚠️ مكتمل جزئياً |
| 4 — التحليل الدلالي | ❌ غير موجود |
| 5 — توليد الكود | ❌ غير موجود |
| 6 — الواجهات والتنقل | ✅ مكتمل (خارجي) |
| 7 — الطباعة | ✅ مكتمل |
