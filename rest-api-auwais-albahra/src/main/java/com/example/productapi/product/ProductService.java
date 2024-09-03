package com.example.productapi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }




    public Product createProduct(Product product) {
        if (product.getName() == null || product.getPrice() == null) {
            throw new IllegalArgumentException("Product name and price must not be null");
        }
        return productRepository.save(product);
    }


    public List<Product>  getAllProducts() {
            try {
                return productRepository.findAll();
            } catch (Exception e) {
                throw new IllegalStateException("Failed to retrieve products", e);
            }
    }


    public Product getProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("404 Not Found");
        }
        return product.get();
    }


    public Product updateProduct(Long productId, Product productDetails) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("404 Not Found"));

        if (productDetails.getName() != null && !productDetails.getName().isEmpty() &&
                !Objects.equals(product.getName(), productDetails.getName())) {
            product.setName(productDetails.getName());
        }

        if (productDetails.getPrice() != null && !Objects.equals(product.getPrice(), productDetails.getPrice())) {
            product.setPrice(productDetails.getPrice());
        }

        if (productDetails.getDescription() != null && !productDetails.getDescription().isEmpty() &&
                !Objects.equals(product.getDescription(), productDetails.getDescription())) {
            product.setDescription(productDetails.getDescription());
        }

        return productRepository.save(product);
    }


    public boolean deleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }

    }
