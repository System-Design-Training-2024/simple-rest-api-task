package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product newProduct) {
        Product savedProduct = productRepository.save(newProduct);
        return savedProduct;
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    public Product getProductById(UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);

        // No product found with such id
        if (!productOptional.isPresent()) {
            throw new IllegalArgumentException("No product found with such id");
        }

        return productOptional.get();
    }

    public Product updateProductById(UUID id, Product updatedProduct) {
        Product targetProduct = this.getProductById(id);

        if (updatedProduct.getName() != null) {
            if (updatedProduct.getName().isEmpty()) {
                throw new IllegalStateException("Name must contains at least 1 character");
            }
            targetProduct.setName(updatedProduct.getName());
        }

        if (updatedProduct.getPrice() != null) {
            if (updatedProduct.getPrice() <= 0) {
                throw new IllegalStateException("Price must be greater than 0");
            }
            targetProduct.setPrice(updatedProduct.getPrice());
        }

        if (updatedProduct.getDescription() != null) {
            targetProduct.setDescription(updatedProduct.getDescription());
        }

        Product savedProduct = productRepository.save(targetProduct);
        return savedProduct;
    }

    public void deleteProductById(UUID id) {
        Product targetProduct = this.getProductById(id);
        productRepository.deleteById(id);
    }
}