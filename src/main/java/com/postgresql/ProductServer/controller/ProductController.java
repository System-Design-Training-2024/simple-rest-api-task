package com.postgresql.ProductServer.controller;

import com.postgresql.ProductServer.Product;
import com.postgresql.ProductServer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public List<Product> registerNewProduct(@RequestBody Product newProduct) {
        return productService.addNewProduct(newProduct);
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("{id}")
    public List<Product> getProductById(@PathVariable("id") Long id) {
        return productService.findProductById(id);
    }

    @PutMapping("{id}")
    public List<Product> updateProduct(@RequestBody Product newProductData, @PathVariable("id") Long id) {
        return productService.updateExistProduct(newProductData, id);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleteExistProduct(id);
    }
}
