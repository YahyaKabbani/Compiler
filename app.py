from flask import Flask, render_template, request, redirect
import json, os

app = Flask(__name__)
UPLOAD = "static/uploads"


def read():
    return json.load(open("products.json"))


def write(data):
    json.dump(data, open("products.json", "w"), indent=2)


@app.route("/")
def home():
    return render_template("index.html", products=read())


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

    return render_template("add.html")


@app.route("/product/<int:id>")
def product(id):
    for p in read():
        if p["id"] == id:
            return render_template("details.html", product=p)


app.run(debug=True, port=5001)
