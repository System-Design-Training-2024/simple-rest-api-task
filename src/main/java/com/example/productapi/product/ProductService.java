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
        return productRepository.findById(productId).orElse(null);
    }

    public Product updateProduct(Long productId, Product productDetails) {
        return productRepository.findById(productId)
                .map(product -> {
                    if (productDetails.getName() != null && !productDetails.getName().isEmpty()) {
                        product.setName(productDetails.getName());
                    }
                    if (productDetails.getPrice() != null) {
                        product.setPrice(productDetails.getPrice());
                    }
                    if (!productDetails.getDescription().isEmpty()) {
                        product.setDescription(productDetails.getDescription());
                    }
                    return productRepository.save(product);
                })
                .orElse(null);
    }


    public boolean deleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }

    }
