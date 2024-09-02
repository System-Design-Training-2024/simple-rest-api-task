from flask import Flask, request, jsonify, abort
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.dialects.postgresql import UUID
from datetime import datetime
import uuid

app = Flask(__name__)

# PostgreSQL database configuration
app.config['SQLALCHEMY_DATABASE_URI'] = 'postgresql://postgres:Beso05Slayer2020database@localhost/postgres'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)

class Product(db.Model):
    __tablename__ = 'products'
    id = db.Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    name = db.Column(db.String(100), nullable=False)
    description = db.Column(db.String(200))
    price = db.Column(db.Numeric(10, 2), nullable=False)
    created_at = db.Column(db.DateTime, default=datetime.now(), nullable=False)
    updated_at = db.Column(db.DateTime, default=datetime.now(), onupdate=datetime.now(), nullable=False)

# Create tables if they don't exist
with app.app_context():
    db.create_all()

@app.route('/products', methods=['POST'])
def create_product():
    data = request.get_json()
    name = data.get('name')
    description = data.get('description')
    price = data.get('price')

    if not name or not price:
        abort(400, description="Name and price are required fields")

    try:
        product = Product(name=name, description=description, price=price)
        db.session.add(product)
        db.session.commit()

        return jsonify({
            'id': str(product.id),
            'name': product.name,
            'description': product.description,
            'price': str(product.price),
            'created_at': product.created_at,
            'updated_at': product.updated_at
        }), 201
    except Exception as e:
        db.session.rollback()
        return jsonify({'error': str(e)}), 500


@app.route('/products', methods=['GET'])
def get_products():
    products = Product.query.all()
    return jsonify([
        {
            'id': product.id,
            'name': product.name,
            'description': product.description,
            'price': str(product.price),
            'created_at': product.created_at,
            'updated_at': product.updated_at
        } for product in products
    ])


@app.route('/products/<uuid:id>', methods=['GET'])
def get_product(id):
    product = Product.query.get_or_404(id)
    return jsonify({
        'id': product.id,
        'name': product.name,
        'description': product.description,
        'price': str(product.price),
        'created_at': product.created_at,
        'updated_at': product.updated_at
    })


@app.route('/products/<uuid:id>', methods=['PUT'])
def update_product(id):
    product = Product.query.get_or_404(id)
    data = request.get_json()

    product.name = data.get('name', product.name)
    product.description = data.get('description', product.description)
    product.price = data.get('price', product.price)

    db.session.commit()

    return jsonify({
        'id': product.id,
        'name': product.name,
        'description': product.description,
        'price': str(product.price),
        'created_at': product.created_at,
        'updated_at': product.updated_at
    })


@app.route('/products/<uuid:id>', methods=['DELETE'])
def delete_product(id):
    product = Product.query.get_or_404(id)
    db.session.delete(product)
    db.session.commit()

    return '', 204


if __name__ == '__main__':
    app.run(debug=True)
