const bodyParser = require('body-parser');
const express = require('express');
const { Pool } = require('pg');

const end_app = express();
const port = 9500;
const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'postgres',
  password: 'anas33',
  port: 5050,
});
app.use(bodyParser.json());


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



  app.get('/products', async (req, res) => {
    try {
      const client = await pool.connect();
      const query = 'SELECT * FROM products';
      const result = await client.query(query);
      client.release();
  
      res.json(result.rows);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Internal server error'});
    }
  });
  


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



app.post('/products', async (req, res) => {
    try {
      const { name, description, price } = req.body;
  
      const client = await pool.connect();
      const query = 'INSERT INTO products (name, description, price) VALUES ($1, $2, $3) RETURNING *';
      const values = [name, description, price];
      const result = await client.query(query, values);
      client.release();
  
      res.status(201).json(result.rows[0]);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Internal server error' });
    }
  });
  app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
  });