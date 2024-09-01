package com.example.REST_API.service;

import com.example.REST_API.entities.Product;
import com.example.REST_API.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id : " + id + " not found!")
        );
    }

    public Exception delete(Integer id) {
        if (!productRepository.existsById(id)) {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id : " + id + " not found!");
        }
        productRepository.deleteById(id);
        return new ResponseStatusException(HttpStatus.NO_CONTENT, "Product with : " + id + " deleted successfully.");
    }

    public Product update(int id, Product product) throws IllegalArgumentException {
        Product curProduct = getProductById(id);
        curProduct.setName(product.getName());
        curProduct.setDescription(product.getDescription());
        curProduct.setPrice(product.getPrice());
        curProduct.setUpdated_at(LocalDateTime.now());
        return productRepository.save(curProduct);
    }
}
