package com.proj.anaas.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService prodserv;

    @Autowired
    public ProductController(ProductService prodserv) {
        this.prodserv = prodserv;
    }

    @GetMapping
    public List<Product> GetProduct(){
        return prodserv.GetProduct();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(prodserv.getProductById(id).orElse(null));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping
    public ResponseEntity<Product> NewProduct(@RequestBody Product product) {
        try {
            Product prod = prodserv.NewProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(prod);
        }catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteProduct(@PathVariable Long id) {
        try {
            prodserv.DeleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> UpdateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product prod = prodserv.UpdateProduct(id, product);
            return ResponseEntity.status(HttpStatus.CREATED).body(prod);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
