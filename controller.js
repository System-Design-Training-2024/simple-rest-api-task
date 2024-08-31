const pool = require("../../db");
const queries = require('./queries');

const getProducts = (req, res) => {
    pool.query(queries.getProducts, (error, results) => {
        if (error) throw error;
        res.status(200).json(results.rows);
    });
};


const CheckIdExists = (req, res) => {
    const id = parseInt(req.params.id);

    pool.query(queries.CheckIdExists, [id], (error, results) => {
        if (error) throw error;
        res.status(200).json(results.rows);
    });
};

const getProductById = (req, res) => {
    const id = parseInt(req.params.id);

    pool.query(queries.getProductById, [id], (error, results) => {
        if (error) throw error;
        
        if (results.rows.length === 0) {
            res.status(404).send('404 Not Found');
        }

        res.status(200).json(results.rows);
    });
};


const addProduct = (req, res) => {
    const { name, description, price } = req.body;

    pool.query(queries.addProduct, [name, description, price], (error, results) => {
        if (error) throw error;
        res.status(201).send("Product Created Successfully!");
    });
};


const removeProduct = (req, res) => {
    const id = parseInt(req.params.id);

    pool.query(queries.getProductById, [id], (error, results) => {
        const noProductFound = !results.rows.length;
        if(noProductFound) {
            res.send("404 Not Found");
        }

        pool.query(queries.removeProduct, [id], (error, results) => {
            if(error) throw error;
            res.status(200).send("204 No Content");
        })
    });
};



const updateProduct = (req, res) => {
    const id = parseInt(req.params.id);
    const { name, description, price } = req.body;

    pool.query(queries.getProductById, [id], (error, results) => {
        const noProductFound = !results.rows.length;
        if (noProductFound) {
            res.status(404).send("404 Not Found");
        }

        // Update the product with name, description, and price
        pool.query(queries.updateProduct, [name, description, price, id], (error, results) => {
            if (error) throw error;
            res.status(200).send("Product updated successfully");
        });
    });
};


module.exports = {
    getProducts,
    getProductById,
    addProduct,
    CheckIdExists,
    removeProduct,
    updateProduct,
};