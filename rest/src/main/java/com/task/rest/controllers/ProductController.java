package com.task.rest.controllers;

import com.task.rest.DTO.ProductDTO;
import com.task.rest.model.Product;
import com.task.rest.repositories.ProductRepository;
import com.task.rest.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/products")
public class ProductController {
    final ProductServices productServices;
    private static Logger logger =
            Logger.getLogger(ProductController.class.getName());

    public ProductController(ProductServices productServices) {
        this.productServices = productServices;
    }


    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        logger.info("Received product: " + product);

        if(!productServices.validateProduct(product)) {
            logger.info("request failed");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        logger.info("request has been done successfully");

        productServices.createProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() {
        logger.info("Received request to get all products");

        List<Product> products = productServices.getAllProducts();
        logger.info("request has been done successfully");

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductId(@PathVariable int id) {
        logger.info("Received request to get product by id");


        if(!productServices.validateProductId(id)) {
            logger.info("request failed");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        logger.info("request has been done successfully");

        Product product = productServices.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody() ProductDTO productDTO) {
        logger.info("Received request to update product by id");

        if(!productServices.validateProductId(id)) {
            logger.info("request failed: 'ID can't be found' !");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


        Product product = productServices.updateProduct(id, productDTO);
        logger.info("request has been done successfully");

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        logger.info("Received request to delete product by id");

        if(!productServices.validateProductId(id)) {
            logger.info("Request failed: id can't be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        productServices.deleteProductById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
