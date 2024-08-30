const client = require('../connect.js'); // PostgreSQL client




// Create a Product
exports.createProduct = async (req, res) => {
    const { name, description, price } = req.body;
    try 
    {
        const result = await client.query
        (
            'INSERT INTO products (name, description, price, created_at, updated_at) VALUES ($1, $2, $3, NOW(), NOW()) RETURNING *',
            [name, description, price]
        );
        res.status(201).json(result.rows[0]);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};





// Get All Products
exports.getAllProducts = async (req, res) => {
    try {
        const result = await client.query('SELECT * FROM products');
        res.status(200).json(result.rows);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};






// Get a Product by ID
exports.getProductById = async (req, res) => {
    const { id } = req.params;
    try {
        const result = await client.query('SELECT * FROM products WHERE id = $1', [id]);
        if (result.rows.length === 0) {
            return res.status(404).json({ error: 'Product not found' });
        }
        res.status(200).json(result.rows[0]);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};








// Update a Product
exports.updateProduct = async (req, res) => {
    const { id } = req.params;
    const { name, description, price } = req.body;
    try {
        const result = await client.query(
            'UPDATE products SET name = $1, description = $2, price = $3, updated_at = NOW() WHERE id = $4 RETURNING *',
            [name, description, price, id]
        );
        if (result.rows.length === 0) {
            return res.status(404).json({ error: 'Product not found' });
        }
        res.status(200).json(result.rows[0]);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};






// Delete a Product
exports.deleteProduct = async (req, res) => {
    const { id } = req.params;
    try {
        const result = await client.query('DELETE FROM products WHERE id = $1 RETURNING *', [id]);
        if (result.rows.length === 0) {
            return res.status(404).json({ error: 'Product not found' });
        }
        res.status(204).send();
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};
