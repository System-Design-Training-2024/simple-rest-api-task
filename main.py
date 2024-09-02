import psycopg2
from psycopg2 import sql
from flask import Flask, request, jsonify, Response

app = Flask(__name__)

def start_connectin():
    db = psycopg2.connect(host="localhost", dbname = "postgres", user = "postgres", password = "123456", port = 5432)

    create_table_query = """
        CREATE TABLE IF NOT EXISTS Product (
            id SERIAL PRIMARY KEY,
            name VARCHAR(100) NOT NULL,
            description VARCHAR(100) NOT NULL,
            price INT NOT NULL,
            created_at TIMESTAMPTZ DEFAULT NOW(),
            updated_at TIMESTAMPTZ DEFAULT NOW()
        );
        """

    cur = db.cursor()

    cur.execute(create_table_query)

    db.commit()

    return db

@app.route("/products",methods=['GET', 'POST'])
def products():

    if request.method == "POST":
        name = request.args.get('name')
        description = request.args.get('description')
        price = request.args.get('price')

        db = start_connectin()
        cur = db.cursor()

        insert_query = f"""
            INSERT INTO Product (name, description, price, created_at, updated_at) 
            VALUES ('{name}', '{description}', '{price}', NOW(), NOW());
            """
        
        cur.execute(insert_query)

        select_query = f"SELECT * FROM Product WHERE name = '{name}';"
        cur.execute(select_query)

        data = cur.fetchall()[0]

        json = {"id" : data[0], "name" : data[1], "description" : data[2], "price": data[3], "created_at" : data[4], "updated_at" : data[5]}
        
        db.commit()

        return jsonify(json)
    
    elif request.method == "GET":
        db = start_connectin()
        cur = db.cursor()

        select_query = f"SELECT * FROM Product"
        cur.execute(select_query)

        data = cur.fetchall()
        arr = []

        for dt in data:
            json = {"id" : dt[0], "name" : dt[1], "description" : dt[2], "price": dt[3], "created_at" : dt[4], "updated_at" : dt[5]}
            arr.append(json)
        
        return arr

@app.route("/products/<id>",methods=['GET', 'POST', 'DELETE', 'PUT'])
def product_delete(id):
    if request.method == "DELETE":

        db = start_connectin()
        cur = db.cursor()

        select_query = f"SELECT EXISTS(SELECT '{id}' FROM Product WHERE id = '{id}');"

        cur.execute(select_query)
        exists = cur.fetchone()[0]

        if exists:

            delete_query = f"DELETE FROM Product WHERE id = '{id}';"
            cur.execute(delete_query)

            db.commit()

            return Response("No Content",status=204)
        
        else:

            return Response("Not Found", status=404)
    
    elif request.method == "PUT":

        db = start_connectin()
        cur = db.cursor()

        

        name = request.args.get('name')
        description = request.args.get('description')
        price = request.args.get('price')


        select_query = f"SELECT * FROM Product WHERE id = '{id}';"
        cur.execute(select_query)
        try:
            data = cur.fetchall()[0]
        except IndexError:
            return Response("Not Found", status=404) 

        json = {"id" : data[0], "name" : data[1], "description" : data[2], "price": data[3], "created_at" : data[4], "updated_at" : data[5]}
        
        if not name:
            name = json["name"]
        
        if not description:
            description = json["description"]
        
        if not price:
            price = json["price"]

        select_query = f"SELECT EXISTS(SELECT '{id}' FROM Product WHERE id = '{id}');"

        cur.execute(select_query)
        exists = cur.fetchone()[0]

        if exists:
            update_query = f"""
                UPDATE Product
                SET name = '{name}', description = '{description}', price = '{price}', updated_at = NOW()
                WHERE id = '{id}';
                """

            cur.execute(update_query)
            db.commit()

            
            return jsonify(json)
        
        else:

            return Response("Not Found", status=404)
    



if __name__ == "__main__":
    app.run(debug=True)