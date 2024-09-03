from flask import Flask , request , jsonify ,abort 
from models import db, Product
import config

app=Flask(__name__)
app.config.from_object(config.Config)
db.init_app(app)

@app.route('/products', methods=['Post'])

def create_product():
    data = request.get_json()
    if not data or 'name' not in data or 'price' not in data:
        abort(400, description="Invalid input")

    product = Product(
        name=data['name'],
        description=data.get('description', ''),
        price=data['price']
    )
    db.session.add(product)
    db.session.commit()
    return jsonify(product.to_dict()), 201


@app.route('/products', methods=['GET'])
def get_all_products():
    products = Product.query.all()
    return jsonify([product.to_dict() for product in products])

@app.route('/products/<int:id>', methods=['GET'])
def get_product(id):
    product = Product.query.get(id)
    if not product:
        abort(404, description="Product not found")
    return jsonify(product.to_dict())

@app.route('/products/<int:id>', methods=['PUT'])
def update_product(id):
    data = request.get_json()
    product = Product.query.get(id)
    if not product:
        abort(404, description="Product not found")

    product.name = data.get('name', product.name)
    product.description = data.get('description', product.description)
    product.price = data.get('price', product.price)
    db.session.commit()
    return jsonify(product.to_dict())

@app.route('/products/<int:id>', methods=['DELETE'])
def delete_product(id):
    product = Product.query.get(id)
    if not product:
        abort(404, description="Product not found")

    db.session.delete(product)
    db.session.commit()
    return '', 204

if __name__ == '__main__':
    with app.app_context():
        db.create_all()
    app.run(debug=True)
