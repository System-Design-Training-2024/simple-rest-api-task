const express = require('express');
const path = require('path');
const app = express();
const ejs = require('ejs');
const client = require('./connect.js');
const bodyParser = require('body-parser');
const productController = require('./controllers/productController'); 

app.set('view engine', 'ejs');
app.use(express.static('public'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));


// Routes for product operations
app.post('/products', productController.createProduct);
app.get('/products', productController.getAllProducts);
app.get('/products/:id', productController.getProductById);
app.put('/products/:id', productController.updateProduct);
app.delete('/products/:id', productController.deleteProduct);

app.listen(4000, () => {
    console.log('App listening on port 4000');
});

client.connect();
