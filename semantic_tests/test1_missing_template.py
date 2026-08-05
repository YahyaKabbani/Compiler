# TEST 1 — MISSING_TEMPLATE
# A copy of app.py where home() renders "indexx.jinja" (typo) — that file does not exist.
# Expected error:
#   [MISSING_TEMPLATE] app.py @line 31 — render_template("indexx.jinja") but the template file does not exist
# How to run:
#   copy app.py app_backup.py
#   copy semantic_tests\test1_missing_template.py app.py
#   run Main, check the report, then restore:
#   copy app_backup.py app.py & del app_backup.py

from flask import Flask, render_template, request, redirect
import json, os

app = Flask(__name__)
UPLOAD = "static/uploads"

PRODUCTS = [
    {"id": 1, "name": "Keyboard", "price": 25, "photo": "kb.png", "details": "Mechanical keyboard"},
    {"id": 2, "name": "Mouse", "price": 15, "photo": "ms.png", "details": "Wireless optical mouse"}
]


def read():
    if os.path.exists("products.json"):
        return json.load(open("products.json"))
    return PRODUCTS


@app.route("/")
def home():
    return render_template("indexx.jinja", products=read())


app.run(debug=True, port=5001)
