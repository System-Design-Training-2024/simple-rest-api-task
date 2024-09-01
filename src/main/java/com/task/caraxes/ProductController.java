package com.task.caraxes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "products")
public class ProductController {

    private final ProductService productService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        }
        catch (ProductNotFoundException e) {

            return ResponseEntity.notFound().build();
        }
    }
    //
    @PostMapping()
    public void createProduct(@RequestBody Product product) {
        productService.createProduct(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        try {
            // Call the service method to delete the product
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build(); // Return 204 No Content
        } catch (ProductNotFoundException e) {
            // Return 404 Not Found if the product does not exist
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product updatedProduct) {
        // Call the service method to update the product
        Product product = productService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok(product); // Return 200 OK with the updated product details
    }
}
