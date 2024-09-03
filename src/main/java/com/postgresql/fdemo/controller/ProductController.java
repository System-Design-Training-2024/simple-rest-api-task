package com.postgresql.fdemo.controller;

import com.postgresql.fdemo.Product.Product;
import com.postgresql.fdemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping (path = "api/v1/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProdect(){
        return productRepository.findAll() ;
    }

    @GetMapping ("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        Product product = productRepository.findById(id).orElseThrow
                (() -> new ResourceNotFoundException("404 Not Found")) ;
        return ResponseEntity.ok(product) ;
    }


    @PostMapping
    public void AddProduct(@RequestBody Product product){
        product.setCreated_at(LocalDate.now());
        product.setUpdated_at(LocalDate.now());
        productRepository.save(product);
    }

    // String name, String description, Double price)
    @PutMapping ("{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestBody Product NewProduct)  {
        Product updateProduct = productRepository.findById(id).orElseThrow
                (() -> new ResourceNotFoundException("404 Not Found")) ;

        updateProduct.setName(NewProduct.getName());
        updateProduct.setDescription(NewProduct.getDescription());
        updateProduct.setPrice(NewProduct.getPrice());
        updateProduct.setUpdated_at(LocalDate.now());

        productRepository.save(updateProduct);

        return ResponseEntity.ok(updateProduct);

    }

    @DeleteMapping ("{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id){
        Product product = productRepository.findById(id).orElseThrow
                ( () -> new ResourceNotFoundException("404 Not Found"));

        productRepository.delete(product);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
