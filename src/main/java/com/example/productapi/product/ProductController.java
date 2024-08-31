package com.example.productapi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        } catch (DataIntegrityViolationException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // Get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Get a product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductByID(@PathVariable("id") Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update a product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductTemp productTemp) {
        try {
            Product p = productService.updateProduct(id,
                    productTemp.getName(),
                    productTemp.getProductDescription(),
                    productTemp.getProductPrice());

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
