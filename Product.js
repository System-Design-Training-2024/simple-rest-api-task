
import express from 'express';
import bodyParser from 'body-parser';

import pkg from 'pg';
const {Pool} = pkg;

const db = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'Product',
  password: 'Bb123456',
  port: 5432,
});


const app = express();
const port = 3000;
app.use(bodyParser.json()); 


const router = express.Router();

// Create a Product
router.post('/Product', async (req, res) => {
  try {
    const { name, description, price } = req.body;
    if (!name || !price) return res.status(400).json({ error: 'Name and price are required' });

    const result = await db.query(
      'INSERT INTO products (name, description, price) VALUES ($1, $2, $3) RETURNING *',
      [name, description, price]
    );

    res.status(201).json(result.rows[0]);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Get All Products
router.get('/Product', async (req, res) => {
  try {
    const result = await db.query('SELECT * FROM products');
    res.status(200).json(result.rows);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Get a Product by ID
router.get('/:id', async (req, res) => {
  try {
    const { id } = req.params;
    const result = await db.query('SELECT * FROM products WHERE id = $1', [id]);
    
    if (result.rows.length === 0) return res.status(404).json({ error: 'Product not found' });
    
    res.status(200).json(result.rows[0]);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Update a Product
router.put('/:id', async (req, res) => {
  try {
    const { id } = req.params;
    const { name, description, price } = req.body;
    const result = await db.query(
      'UPDATE products SET name = $1, description = $2, price = $3, updated_at = NOW() WHERE id = $4 RETURNING *',
      [name, description, price, id]
    );
    
    if (result.rows.length === 0) return res.status(404).json({ error: 'Product not found' });

    res.status(200).json(result.rows[0]);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Delete a Product
router.delete('/:id', async (req, res) => {
  try {
    const { id } = req.params;
    const result = await db.query('DELETE FROM products WHERE id = $1 RETURNING *', [id]);
    
    if (result.rowCount === 0) return res.status(404).json({ error: 'Product not found' });

    res.status(204).send();
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});


app.use('/products', router);


app.listen(port, () => {
  console.log(`Server running on port:${port}`);
});
