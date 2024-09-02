const express = require('express');
const { Pool } = require('pg');
const bodyParser = require('body-parser');

const app = express();
const port = 5000; 

// Connect my database
const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'postgres',
  password: 'wadea1234',
  port: 5050,
});
  
// Middleware
app.use(bodyParser.json());


  
// Get all products (because we don't take ID here) => self notes :)
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
  

  // Update product details
  app.put('/products/:id', async (req, res) => {
    try {
      const id = req.params.id;
      const { name, description, price } = req.body;
  
      const client = await pool.connect();
      const query = 'UPDATE products SET name = $1, description = $2, price = $3 WHERE id = $4 RETURNING *';
      const values = [name, description, price, id];
      const result = await client.query(query, values);
      client.release();
  
      if (result.rows.length === 0) {
        res.status(404).json({ error: 'Product not found' });
      } else {
        res.json(result.rows[0]);
      }
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Internal server error' });
    }
  });



// Create a product
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


  
  
// Delete a product   

app.delete('/products/:id', async (req, res) => {
  try {
    const id = req.params.id;

    const client = await pool.connect();
    const query = 'DELETE FROM products WHERE id = $1 RETURNING *';
    const values = [id];
    const result = await client.query(query, values);
    client.release();

    if (result.rows.length === 0) {
      res.status(404).json({ error: 'Product not found' });
    } else {
      res.status(204).send();
    }
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal server error' });
  }
});

// Get a product by ID
app.get('/products/:id', async (req, res) => {
    try {
      const id = req.params.id;
  
      const client = await pool.connect();
      const query = 'SELECT * FROM products WHERE id = $1';
      const values = [id];
      const result = await client.query(query, values);
      client.release();
  
      if (result.rows.length === 0) {
        res.status(404).json({ error: 'Product not found' });
      } else {
        res.json(result.rows[0]);
      }
    } catch (error) {
      console.error(error);
      res.status(500).json({   
    error: 'Internal server error' });
    }
  });
      



const PORT = process.env.PORT || port;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));