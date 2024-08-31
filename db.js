const Pool = require("pg").Pool;

const pool = new Pool ({
    user: "postgres",
    host: "localhost",
    database: "products",
    password: "Hazem@2005",
    port: 5432,
});

module.exports = pool;