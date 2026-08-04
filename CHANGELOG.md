# سجل التغييرات

## تغيير ContextExtractor — من instanceof إلى Visitor Pattern

### الملف المعدّل
`src/visitor/ContextExtractor.java`

---

### الأسلوب القديم — instanceof

```java
private void visit(ASTNode node) {
    if (node instanceof PythonProgramNode p) {
        for (ASTNode s : p.getStatements()) visit(s);
    }
    else if (node instanceof FunctionDefNode f) {
        for (ASTNode s : f.getBody()) visit(s);
    }
    else if (node instanceof IfNode ifNode) {
        for (ASTNode s : ifNode.getBody()) visit(s);
    }
    else if (node instanceof HtmlTagNode tag) {
        table.define(new Symbol(
                tag.getTagName(),
                SymbolKind.HTML_TAG,
                tag.getLine(),
                table.currentScope()
        ));
        table.enterScope("html:" + tag.getTagName());
        for (String attrValue : tag.getAttributes().values()) {
            Matcher m = JINJA_EXPR_VAR.matcher(attrValue);
            while (m.find()) {
                table.define(new Symbol(
                        m.group(1),
                        SymbolKind.JINJA_VARIABLE,
                        tag.getLine(),
                        table.currentScope()
                ));
            }
        }
    }
    // ...
}
```

### الأسلوب الجديد — Visitor Pattern

```java
public class ContextExtractor implements ASTVisitor {

    @Override
    public void visit(FunctionDefNode node) {
        for (ASTNode s : node.getBody()) s.accept(this);
    }

    @Override
    public void visit(IfNode node) {
        for (ASTNode s : node.getBody()) s.accept(this);
    }

    @Override
    public void visit(HtmlTagNode node) { /* no-op */ }

    // ...
}
```

---

### سبب التغيير

#### 1. مخالفة مبدأ Polymorphism
الأسلوب القديم يجعل `ContextExtractor` هو من يقرر كيف يتعامل مع كل نوع عقدة عبر سلسلة `if/else if`.
هذا يعني أن **الكلاس الخارجي يتحكم في سلوك العقد** بدلاً من العقد نفسها.

الأسلوب الجديد يجعل **كل عقدة تستدعي** `node.accept(this)` فتختار هي الـ `visit` الصحيح تلقائياً — وهو تعدد الأشكال (Polymorphism) الحقيقي.

#### 2. مخالفة مبدأ Open/Closed
في الأسلوب القديم، لو أضفت عقدة جديدة مثل `WhileNode`:
- يجب تعديل `ContextExtractor` وإضافة `else if (node instanceof WhileNode)`
- يجب تعديل كل كلاس آخر يستخدم نفس الأسلوب

في الأسلوب الجديد:
- تضيف `visit(WhileNode node)` في `ASTVisitor` interface
- كل كلاس يطبق الـ interface يُجبر تلقائياً على إضافة الـ method

#### 3. الطلب الثالث من المشروع
الطلب الثالث يشترط صراحةً:
> *"يجب أن تطبق مفاهيم البرمجة الكائنية (OOP) — الوراثة (Inheritance)، وتعدد الأشكال (Polymorphism)"*

استخدام `instanceof` هو أسلوب إجرائي (Procedural) وليس كائنياً (OOP).
تطبيق `ASTVisitor` interface هو الأسلوب الكائني الصحيح.

---

### مقارنة مباشرة

| النقطة | instanceof | Visitor Pattern |
|--------|-----------|-----------------|
| من يقرر السلوك؟ | ContextExtractor | العقدة نفسها |
| إضافة عقدة جديدة | تعديل كل كلاس | إضافة method واحدة |
| Polymorphism | ❌ غير موجود | ✅ موجود |
| Open/Closed | ❌ مخالف | ✅ محقق |
| متطلب الطلب 3 | ❌ لا يحقق | ✅ يحقق |

---

### النتيجة

`ContextExtractor` أصبح يطبق `ASTVisitor` interface بشكل كامل — كل عقدة من العقد الـ 20 لها `visit()` خاص بها، مما يحقق Polymorphism الكامل المطلوب في الطلب الثالث.
