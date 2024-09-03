const { Router } = require("express");
const controller = require("./controller");

const router = Router();

router.get("/", controller.getProducts);
router.post("/", controller.addProduct);
router.get("/:id", controller.getProductById);
router.delete("/:id", controller.removeProduct);
router.put("/:id", controller.updateProduct);

module.exports = router;