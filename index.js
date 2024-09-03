const express = require('express');
const bodyParser = require('body-parser');
const { Pool } = require('pg');

const app = express();
const port = 3000;

app.use(bodyParser.json());

const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'products',
  password: '1234',
  port: 5432,
});


// Create products
app.post('/products', async (req, res) => {
  const { name, description, price } = req.body;
  try {
    const result = await pool.query(
      'INSERT INTO products (name, description, price) VALUES ($1, $2, $3) RETURNING *',
      [name, description, price]
    );
    res.status(201).json(result.rows[0]);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});



// Get all products
app.get('/products', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM products');
    res.json(result.rows);
  } catch (err) {
    res.status(500).json({ error: err.message });  
  }
});



// Get by ID
app.get('/products/:id', async (req, res) => {
  const { id } = req.params;
  try {
    const result = await pool.query('SELECT * FROM products WHERE id = $1', [id]);
    if (result.rows.length === 0) {
      return res.status(404).json({ error: 'Product is not found' });
    }
    res.json(result.rows[0]);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// Update by ID
app.put('/products/:id', async (req, res) => {
  const { id } = req.params;
  const { name, description, price } = req.body;
  try {
    const result = await pool.query(
      'UPDATE products SET name = $1, description = $2, price = $3, updated_at = CURRENT_TIMESTAMP WHERE id = $4 RETURNING *',
      [name, description, price, id]
    );
    if (result.rows.length === 0) {
      return res.status(404).json({ error: 'Product is not found' });
    }
    res.json(result.rows[0]);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// Delete by ID
app.delete('/products/:id', async (req, res) => {
  const { id } = req.params;
  try {
    const result = await pool.query('DELETE FROM products WHERE id = $1 RETURNING *', [id]);
    if (result.rows.length === 0) {
      return res.status(404).json({ error: 'Product is not found' });
    }
    res.status(204).send();
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});


app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
