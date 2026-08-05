from flask import Flask, render_template, request, redirect
import json, os

app = Flask(__name__)
UPLOAD = "static/uploads"
os.makedirs(UPLOAD, exist_ok=True)

PRODUCTS = [
    {"id": 1, "name": "Keyboard", "price": 2502, "photo": "kb.png", "details": "Mechanical keyboard"},
    {"id": 2, "name": "Mouse", "price": 15, "photo": "ms.png", "details": "Wireless optical mouse"}
]


def read():
    if os.path.exists("products.json"):
        return json.load(open("products.json"))
    return PRODUCTS


def write(data):
    json.dump(data, open("products.json", "w"), indent=2)


@app.route("/")
def home():
    return render_template("index.jinja", products=read())


@app.route("/add", methods=["GET", "POST"])
def add():
    if request.method == "POST":
        f = request.files["photo"]
        f.save(os.path.join(UPLOAD, f.filename))

        products = read()
        products.append({
            "id": len(products) + 1,
            "name": request.form["name"],
            "price": request.form["price"],
            "details": request.form["details"],
            "photo": f.filename
        })
        write(products)
        return redirect("/")

    return render_template("add_product.jinja")


@app.route("/delete/<int:id>")
def delete(id):
    products = []
    for p in read():
        if p["id"] < id:
            products.append(p)
        if p["id"] > id:
            products.append(p)
    write(products)
    return redirect("/")


@app.route("/product/<int:id>")
def product(id):
    for p in read():
        if p["id"] == id:
            return render_template("product_details.jinja", product=p)


app.run(debug=True, port=5001)
