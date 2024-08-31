const express = require("express");
const productsRoutes = require("./src/products/routes");

const app = express();
const port = 8000;

app.use(express.json());

app.get("/", (req, res) => {
    res.send("Hello World!");
});

app.use("/api/v1/products", productsRoutes);

app.listen(port, () => console.log(`app listening on port ${port}`));