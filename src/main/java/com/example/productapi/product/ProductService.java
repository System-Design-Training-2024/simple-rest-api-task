package com.example.productapi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(Product product) {
        Optional<Product> productOptional = productRepository.findByProductName(product.getName());
        if (productOptional.isPresent()) {
            throw new IllegalStateException("Product already exists");
        }
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty())
            throw new IllegalStateException("Product not found");
        return productRepository.findById(id).get();
    }

    public void deleteProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new IllegalStateException("Product not found");
        }
        productRepository.deleteById(id);
    }
}
