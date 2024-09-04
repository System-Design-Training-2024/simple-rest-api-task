#         LIBRARIES
import os
import psycopg2
from dotenv import load_dotenv
from flask import Flask, request, jsonify



#           CONSTANT
## SQL QUIRES

CREATE_TABLE_QUERY = '''
CREATE TABLE IF NOT EXISTS product (
id SERIAL PRIMARY KEY,
name VARCHAR(200) NOT NULL,
description TEXT,
price DECIMAL(10, 2) NOT NULL ,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
'''

INSERT_PRODUCT_RETURN_INFO = "INSERT INTO product (name, description, price)\
VALUES (%s, %s, %s) RETURNING (id, name, description, price, created_at, updated_at)"

RETRIEVE_ALL_PRODUCTS = """ SELECT * FROM product;"""
RETRIEVE_BY_ID = """ SELECT * FROM product WHERE id = %s;"""

UPDATE_QUERY = """ UPDATE product 
SET name = COALESCE(%s, name), 
    description = COALESCE(%s, description),
    price = COALESCE(%s, price),
    updated_at = CURRENT_TIMESTAMP
WHERE id = %s
RETURNING *;
"""

DELETE_QUERY = """
DELETE FROM product
WHERE id = %s
RETURNING id;
"""



#       CALLBACKS
def format_output(product):
    product_data = {
        "id": product[0],
        "name": product[1],
        "description": product[2],
        "price": str(product[3]),
        "created_at": product[4].isoformat(),
        "updated_at": product[5].isoformat()
    }
    return product_data


#
# upload the .env file
load_dotenv()


# create an instance from the flask object
app = Flask(__name__ )

# CONNECT TO THE DATABASE AND READ, INSERT DATA TO THE DB
URL = os.getenv("DATABASE_URL")
connection = psycopg2.connect(URL)

with connection:
        with connection.cursor() as cursor: # cursor allow us to insert or iterate over the data
            cursor.execute(CREATE_TABLE_QUERY) 

#psot --> HTTP method used for clients that send us data
@app.post('/products')
def create_product():
    data = request.get_json()
    name = data.get('name')
    desc = data.get('description')
    price = data.get('price')
    values = (name, desc, price)
    with connection:
        with connection.cursor() as cursor: # cursor allow us to insert or iterate over the data
            #cursor.execute(CREATE_TABLE_QUERY)            
            cursor.execute(INSERT_PRODUCT_RETURN_INFO, values)
            product_info = cursor.fetchall()
    
    return format_output(product_info), 201

# GET endpoint to retrieve all products
@app.get('/products')
def get_all_products():
    with connection:
        with connection.cursor() as cursor:
            cursor.execute(RETRIEVE_ALL_PRODUCTS)
            products = cursor.fetchall()
            
    # Converting the result into a list of dictionaries
    products_list = [
        format_output(product)
        for product in products]
    
    return jsonify(products_list), 200

# GET endpoint to retrieve a product by ID
@app.get('/products/<int:product_id>')
def get_product_by_id(product_id):
    with connection:
        with connection.cursor() as cursor:
            cursor.execute(RETRIEVE_BY_ID, (product_id,))
            product = cursor.fetchone()
    
    if product is None:
        return jsonify({"error": "Product not found"}), 404
    else:
        product_data = format_output(product)    
        return jsonify(product_data), 200
    
@app.put('/products/<int:product_id>')
def update_product(product_id):
    data = request.get_json()
    name = data.get('name')
    description = data.get('description')
    price = data.get('price')

    with connection:
        with connection.cursor() as cursor:
            cursor.execute(UPDATE_QUERY, (name, description, price, product_id))
            updated_product = cursor.fetchone()
    
    if updated_product is None:
        return jsonify({"error": "Product not found"}), 404
    
    product_data = format_output(updated_product)
    return jsonify(product_data), 200

@app.delete('/products/<int:product_id>')
def delete(product_id):
    with connection:
        with connection.cursor() as cursor:
            cursor.execute(DELETE_QUERY, (product_id,))
            deleted_product = cursor.fetchone()
    
    if deleted_product is None:
         return jsonify({"error": "product not found"}), 404
    
    else:
        return "", 204 # No content