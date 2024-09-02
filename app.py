import os
from datetime import datetime
import psycopg2
from dotenv import load_dotenv
from flask import Flask
from flask import request
from flask import jsonify
from flask import   make_response


#TODO: Load the inviromental variables from .rnv file 

load_dotenv()


#TODO:  Storing environmental variables for connection
DB_HOST = os.getenv('DB_HOST')
DB_NAME = os.getenv('DB_NAME')
DB_USER = os.getenv('DB_USER')
DB_PASSWORD = os.getenv('DB_PASSWORD')
DB_PORT = os.getenv('DB_PORT') 


#TODO: Connect to the DB

connection = psycopg2.connect(
    host = DB_HOST,
    database = DB_NAME,
    user = DB_USER,
    password = DB_PASSWORD,
    port = DB_PORT
                              )




#TODO:  connect the cursor 

cursor = connection.cursor()

#TODO: Create the table & Trigger/ Trigger function

CREATE_PRODUCT_TABLE = """CREATE TABLE IF NOT EXISTS PRODUCT (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(50) NOT NULL,
                          description TEXT,
                          price DECIMAL NOT NULL, 
                          created_at timestamp with time zone 
                          DEFAULT CURRENT_TIMESTAMP,
                          updated_at timestamp with time zone 
                          DEFAULT CURRENT_TIMESTAMP );"""




CREATE_TRIGGER_FUNCTION_AND_TRIGGER = """
                           DO $do$
                           BEGIN
                              IF NOT EXISTS (
                                 SELECT 1 
                                 FROM pg_trigger 
                                 WHERE tgname = 'set_timestamp'
                                            ) 
                            THEN
                            -- Create the trigger function if not exists
                            CREATE OR REPLACE FUNCTION 
                            update_modified_column()
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


#TODO: Starting a Flask application

app = Flask(__name__)  

#TODO: Create endpoints 


#? 1.Create a Product Endpoint 

CREATE_PRODUCT =    (       
                        """INSERT INTO PRODUCTS (name,description,price) 
                         VALUES (%s,%s,%s) RETURNING id, name, price, created_at;"""
                    )




@app.post("/products")
def create_product():
    data  =   request.get_json()
    name  =   data["name"]
    price =   data["price"]
    description = data["description"]
    with connection:
            cursor.execute(CREATE_PRODUCT_TABLE)
            cursor.execute(CREATE_TRIGGER_FUNCTION_AND_TRIGGER)
            cursor.execute(CREATE_PRODUCT,(name,description,price))
            product = cursor.fetchone()[:]
    return make_response(jsonify(product), 201)


#? 2.Get All Products Endpoint

GET_ALL_PRODUCTS =  (           
                         """SELECT * FROM PRODUCTS;"""
                    )




@app.get("/products")
def get_all_products():
    with connection:
            cursor.execute(GET_ALL_PRODUCTS)
            retrieved= cursor.fetchall()[:]
    return jsonify(retrieved), 200

#? 3. Get a Single Product Endpoint

GET_PRODUCT_BY_ID = (
                        """SELECT * FROM PRODUCTS where id= ;"""
                    )



@app.get("/products/<int:id>")
def get_product_by_id(id):
    with connection:
            try :
                cursor.execute("SELECT * FROM products where id=%s ;", (id,))
                product=cursor.fetchone()[:]
            except :
                return make_response(jsonify({"error": "Product not found !!!"}), 404)
    return make_response(jsonify(product),200)

#? 4. Update a Product Endpoint


UPDATE_PRODUCT =    (     
                        """UPDATE products
                        SET name = %s, description= %s, price=%s
                        WHERE id = %s RETURNING id,name,price,
                        description,updated_at; """
                    )




@app.put("/products/<int:id>")
def update_product(id):
    data = request.get_json()
    id = id
    name =  data["name"]
    price = data["price"]
    description = data["description"]
    
    with connection :

            
            cursor.execute("SELECT * FROM products WHERE id = %s", (id,))
            product = cursor.fetchone()
            if not product :
                 return make_response(jsonify({'error': 'Product not found !!!'}), 404)
            
            cursor.execute(UPDATE_PRODUCT,(name,description,price,id))
            updated_product = cursor.fetchone()[:]
    return make_response(jsonify(updated_product), 200)

#? 5. Delete a Product Endpoint

DELETE_PRODUCT =    (
                         """DELETE FROM products
                         WHERE id = %s ;"""
                    )



@app.delete("/products/<int:id>")
def delete_product(id) :
    with connection :
            
            cursor.execute("SELECT * FROM PRODUCTS WHERE id=%s", (id,))
            prod = cursor.fetchone()
            if not prod :
                return make_response(jsonify({'error': 'Product not found !!!'}),404)
            
            cursor.execute(DELETE_PRODUCT, (id,))
    return make_response( jsonify({"popup": "Product Not Found"}), 204)

@app.get("/")

def hello():
      return "Hello, Wellcome to My Product Management API!"
  
