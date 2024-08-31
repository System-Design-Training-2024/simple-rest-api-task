const getProducts = "SELECT * FROM products";
const addProduct = "INSERT INTO products (name, description, price) VALUES ($1, $2, $3)";
const getProductById = "SELECT * FROM products WHERE id = $1";
const CheckIdExists = "SELECT s FROM products s WHERE id = $1";
const removeProduct = "DELETE FROM products WHERE id = $1";
const updateProduct = "UPDATE products SET name = $1, description = $2, price = $3, updated_at = CURRENT_TIMESTAMP WHERE id = $4";


module.exports = {
    getProducts,
    getProductById,
    CheckIdExists,
    addProduct,
    removeProduct,
    updateProduct,
};