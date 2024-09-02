import os
from datetime import datetime
import psycopg2
from dotenv import load_dotenv
from flask import Flask, request, jsonify, make_response

# Load environment variables from .env file
load_dotenv()

# Store environment variables for database connection
DB_HOST = os.getenv('DB_HOST')
DB_NAME = os.getenv('DB_NAME')
DB_USER = os.getenv('DB_USER')
DB_PASSWORD = os.getenv('DB_PASSWORD')
DB_PORT = os.getenv('DB_PORT')

# Connect to the database
def get_db_connection():
    return psycopg2.connect(
        host=DB_HOST,
        database=DB_NAME,
        user=DB_USER,
        password=DB_PASSWORD,
        port=DB_PORT
    )

def setup_database():
    connection = get_db_connection()
    try:
        with connection.cursor() as cursor:
            # Create the product table
            cursor.execute("""
                CREATE TABLE IF NOT EXISTS products (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(50) NOT NULL,
                    description TEXT,
                    price DECIMAL NOT NULL, 
                    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
                );
            """)

            # Create the trigger function and trigger
            cursor.execute("""
                DO $$
                BEGIN
                    IF NOT EXISTS (
                        SELECT 1 
                        FROM pg_trigger 
                        WHERE tgname = 'set_timestamp'
                    ) THEN
                        -- Create the trigger function
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
                END $$;
            """)
        connection.commit()
    finally:
        connection.close()

# Run the setup function to ensure the database is ready
setup_database()

# Initialize Flask application
app = Flask(__name__)

# Endpoints

@app.post("/products")
def create_product():
    data = request.get_json()
    name = data["name"]
    price = data["price"]
    description = data["description"]

    connection = get_db_connection()
    try:
        with connection.cursor() as cursor:
            cursor.execute("""
                INSERT INTO products (name, description, price) 
                VALUES (%s, %s, %s) RETURNING id, name, price, created_at;
            """, (name, description, price))
            product = cursor.fetchone()
            connection.commit()
    finally:
        connection.close()

    return make_response(jsonify(product), 201)

@app.get("/products")
def get_all_products():
    connection = get_db_connection()
    try:
        with connection.cursor() as cursor:
            cursor.execute("SELECT * FROM products;")
            retrieved = cursor.fetchall()
    finally:
        connection.close()

    return jsonify(retrieved), 200

@app.get("/products/<int:id>")
def get_product_by_id(id):
    connection = get_db_connection()
    try:
        with connection.cursor() as cursor:
            cursor.execute("SELECT * FROM products WHERE id=%s;", (id,))
            product = cursor.fetchone()
            if not product:
                return make_response(jsonify({"error": "Product not found"}), 404)
    finally:
        connection.close()

    return make_response(jsonify(product), 200)

@app.put("/products/<int:id>")
def update_product(id):
    data = request.get_json()
    name = data["name"]
    price = data["price"]
    description = data["description"]

    connection = get_db_connection()
    try:
        with connection.cursor() as cursor:
            cursor.execute("SELECT * FROM products WHERE id=%s;", (id,))
            product = cursor.fetchone()
            if not product:
                return make_response(jsonify({"error": "Product not found"}), 404)
            
            cursor.execute("""
                UPDATE products
                SET name = %s, description = %s, price = %s
                WHERE id = %s RETURNING id, name, price, description, updated_at;
            """, (name, description, price, id))
            updated_product = cursor.fetchone()
            connection.commit()
    finally:
        connection.close()

    return make_response(jsonify(updated_product), 200)

@app.delete("/products/<int:id>")
def delete_product(id):
    connection = get_db_connection()
    try:
        with connection.cursor() as cursor:
            cursor.execute("SELECT * FROM products WHERE id=%s;", (id,))
            product = cursor.fetchone()
            if not product:
                return make_response(jsonify({"error": "Product not found"}), 404)
            
            cursor.execute("DELETE FROM products WHERE id=%s;", (id,))
            connection.commit()
    finally:
        connection.close()

    return make_response(jsonify({"message": "Product deleted"}), 204)

@app.get("/")
def hello():
    return "Hello, welcome to my Product Management API!"

if __name__ == "__main__":
    app.run(debug=True)
