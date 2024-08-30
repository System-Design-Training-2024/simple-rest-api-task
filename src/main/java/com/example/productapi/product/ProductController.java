package com.example.productapi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Create a new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        productService.createProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // Get all products
    @GetMapping
    public ResponseEntity<String> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        if (productList.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(productList.toString());
    }

    // Get a product by ID
    @GetMapping("/{id}")
    public ResponseEntity<String> getProductByID(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        if (product == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(product.toString());
    }

    // Update a product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable("id") Long id,
            @RequestBody Product product) {
        Product p = productService.getProductById(id);
        if (p == null)
            return ResponseEntity.notFound().build();

        productService.updateProduct(id, product);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }


    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        Product p = productService.getProductById(id);
        if (p == null)
            return ResponseEntity.notFound().build();
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
