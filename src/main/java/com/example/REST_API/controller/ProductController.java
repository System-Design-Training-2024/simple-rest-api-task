package com.example.REST_API.controller;

import com.example.REST_API.entities.Product;
import com.example.REST_API.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("test")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("add")
    public Product addProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @GetMapping("/getAll")
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getProduct/{id}")
    public Product getProduct(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/delete/{id}")
    public Exception deleteProduct(@PathVariable int id) {
        return productService.delete(id);
    }

    @PutMapping("/update/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
        return productService.update(id, product);
    }
}