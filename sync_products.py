import json
import os
import re

SOURCE = "app.py"
DATA = "products.json"


def clean(value):
    return str(value).replace('"', "'").replace("\\", "/")


def render(products):
    rows = []
    for p in products:
        row = ('    {"id": ' + json.dumps(p.get("id"))
               + ', "name": "' + clean(p.get("name", "")) + '"'
               + ', "price": ' + json.dumps(p.get("price", ""))
               + ', "photo": "' + clean(p.get("photo", "")) + '"'
               + ', "details": "' + clean(p.get("details", "")) + '"}')
        rows.append(row)
    return "PRODUCTS = [\n" + ",\n".join(rows) + "\n]"


def main():
    if not os.path.exists(DATA):
        print("no " + DATA + " - nothing to sync (app.py is already the source of truth)")
        return

    products = json.load(open(DATA, encoding="utf-8"))
    block = render(products)

    source = open(SOURCE, encoding="utf-8").read()
    updated, count = re.subn(r"PRODUCTS = \[.*?\n\]", lambda m: block, source, count=1, flags=re.S)
    if count == 0:
        print("could not find the PRODUCTS array in " + SOURCE)
        return

    open(SOURCE, "w", encoding="utf-8", newline="\n").write(updated)
    os.remove(DATA)

    print("synced " + str(len(products)) + " product(s) from " + DATA + " into PRODUCTS in " + SOURCE)
    print("removed " + DATA + " so app.py is the single source of truth")
    print("now run Main to regenerate output/")


main()
