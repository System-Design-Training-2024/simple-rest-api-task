package com.postgresql.ProductServer.service;

import com.postgresql.ProductServer.Product;
import com.postgresql.ProductServer.ProductServerApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> addNewProduct(Product product) {
        List<Product> result = new ArrayList<>();
        product.setCreated_at(LocalDateTime.now());
        product.setUpdated_at(LocalDateTime.now());
        productRepository.save(product);
        Optional<Product> productOptional = productRepository.findById(product.getId());
        if (productOptional.isPresent()) {
            result.add(productOptional.get());
        }
        return result;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> findProductById(Long id) {
        List<Product> result = new ArrayList<>();
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new IllegalStateException("404 not found");
        }
        if (productOptional.isPresent()) {
            result.add(productOptional.get());
        }
        return result;
    }

    public List<Product> updateExistProduct(Product newProductData, Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new IllegalStateException("404 not found");
        }
        Product upadetedProduct = productOptional.get();
        upadetedProduct.setName(newProductData.getName());
        upadetedProduct.setDescription(newProductData.getDescription());
        upadetedProduct.setPrice(newProductData.getPrice());
        newProductData.setUpdated_at(LocalDateTime.now());
        productRepository.save(upadetedProduct);
        return findProductById(id);
    }

    public void deleteExistProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new IllegalStateException("404 not found");
        }
        productRepository.deleteById(id);
    }
}
