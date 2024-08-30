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
        try { 
           productService.createProduct(product);
        } catch (IllegalStateException e) {
          return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // Get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> productList = productService.getAllProducts();
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get a product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductByID(@PathVariable("id") Long id) {
        try {
            Product product = productService.getProductById(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update a product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable("id") Long id,
            @RequestBody Product product) {
        try {
            Product p = productService.updateProduct(id, product);
            return new ResponseEntity<>(p, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }


    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
