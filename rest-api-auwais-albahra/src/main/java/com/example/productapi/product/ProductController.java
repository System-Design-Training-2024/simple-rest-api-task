package com.example.productapi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }


    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(path = "{productId}")
    public Product getProduct(@PathVariable Long productId) {
       return productService.getProduct(productId);
    }

    @PutMapping(path = "{productId}")
    public Product updateProduct(@PathVariable Long productId, @RequestBody Product productDetails) {
        return productService.updateProduct(productId, productDetails);
    }

    @DeleteMapping(path = "{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId) {
        boolean isDeleted = productService.deleteProduct(productId);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
