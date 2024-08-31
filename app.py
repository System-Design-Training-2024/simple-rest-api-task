import os
import psycopg2
from flask import Flask, jsonify, make_response
from flask import request
from dotenv import load_dotenv

CREATE_PRODUCTS_TABLE="""Create table if not exists PRODUCTS(
        id SERIAL PRIMARY KEY, 
        name character varying(25) NOT NULL,
        description TEXT,
        price DECIMAL NOT NULL,
        created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
        updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP);"""

CREATE_TRIGGER_FUNCTION_AND_TRIGGER = """
DO $do$
BEGIN
   IF NOT EXISTS (
       SELECT 1 
       FROM pg_trigger 
       WHERE tgname = 'set_timestamp'
    ) THEN
       -- Create the trigger function if not exists
       CREATE OR REPLACE FUNCTION update_modified_column()
       RETURNS TRIGGER AS $$
       BEGIN
           NEW.updated_at = NOW();
           RETURN NEW;
       END;
       $$ LANGUAGE plpgsql;

       -- Create the trigger
       CREATE TRIGGER set_timestamp
       BEFORE UPDATE ON products
       FOR EACH ROW
       EXECUTE FUNCTION update_modified_column();
   END IF;
END $do$;"""

ADD_PRODUCT_TO_PRODUCTS=(
    "INSERT INTO PRODUCTS (name,description,price) VALUES (%s,%s,%s) RETURNING id, name, price, created_at;"
)

RETRIEVE_ALL=("SELECT * FROM PRODUCTS;")

RETRIEVE_PRODUCT_BY_ID=("SELECT * FROM PRODUCTS where id= ;")

UPDATE_EXISTING_PRODUCT="""UPDATE products
        SET name = %s, description= %s, price=%s
        WHERE id = %s RETURNING id,name,price,description,updated_at; """


DELETE_PRODUCT_BY_ID=(
    """DELETE FROM products
       WHERE id = %s ;"""
)

# Load environment variables
load_dotenv()

# Database connection details from environment variables
DB_HOST = os.getenv('DB_HOST')
DB_NAME = os.getenv('DB_NAME')
DB_USER = os.getenv('DB_USER')
DB_PASSWORD = os.getenv('DB_PASSWORD')
DB_PORT = os.getenv('DB_PORT')


# Create connection to a database
connection= psycopg2.connect(
    host=DB_HOST,
    database=DB_NAME,
    user=DB_USER,
    password=DB_PASSWORD,
    port=DB_PORT
)

app=Flask(__name__)

@app.post("/products")
def create_product():
    data=request.get_json()
    name=data["name"]
    price=data["price"]
    description=data["description"]
    with connection:
        with connection.cursor() as cursor:
            cursor.execute(CREATE_PRODUCTS_TABLE)
            cursor.execute(CREATE_TRIGGER_FUNCTION_AND_TRIGGER)
            cursor.execute(ADD_PRODUCT_TO_PRODUCTS,(name,description,price))
            product=cursor.fetchone()[:]
    return make_response(jsonify(product), 201)

@app.get("/products")
def get_all_products():
    with connection:
        with connection.cursor() as cursor:
            cursor.execute(RETRIEVE_ALL)
            retrieved= cursor.fetchall()[:]
    return jsonify(retrieved), 200

@app.get("/products/<int:id>")
def get_product_by_id(id):
    with connection:
        with connection.cursor() as cursor:
            try:
                cursor.execute("SELECT * FROM products where id=%s ;", (id,))
                product=cursor.fetchone()[:]
            except:
                return make_response(jsonify({"error": "Product does not exist"}), 404)
    return make_response(jsonify(product),200)

@app.put("/products/<int:id>")
def update_product(id):
    data=request.get_json()
    id=id
    name=data["name"]
    price=data["price"]
    description=data["description"]
    
    with connection:
        with connection.cursor() as cursor:

            ###check if product exists
            cursor.execute("SELECT * FROM products WHERE id = %s", (id,))
            product = cursor.fetchone()
            if not product:
                 return make_response(jsonify({'error': 'Product not found'}), 404)
            
            cursor.execute(UPDATE_EXISTING_PRODUCT,(name,description,price,id))
            updated_product=cursor.fetchone()[:]
    return make_response(jsonify(updated_product), 200)

@app.delete("/products/<int:id>")
def delete_product(id):
    with connection:
        with connection.cursor() as cursor:
            
            ###check if product exists
            cursor.execute("SELECT * FROM PRODUCTS WHERE id=%s", (id,))
            prod= cursor.fetchone()
            if not prod:
                return make_response(jsonify({'error': 'Product not found'}),404)
            
            ###if product exists delete it
            cursor.execute(DELETE_PRODUCT_BY_ID, (id,))
    return make_response( jsonify({"message": "Content Not Found"}), 204)