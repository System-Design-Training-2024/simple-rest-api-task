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
        // !! It is ok to have multiple products with the same name, each new product has its own unique ID.
        // Optional<Product> productOptional = productRepository.findByProductName(product.getName());
        // if (productOptional.isPresent())
        // throw new IllegalStateException("Product already exists (by name).");
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty())
            throw new IllegalStateException("Product (ID) not found.");
        return productOptional.get();
    }

    public Product updateProduct(Long id, Product newProductInfo) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty())
            throw new IllegalStateException("Product (ID) not found.");
        Product p = existingProduct.get();
        if (newProductInfo.getName() != null)
            p.setName(newProductInfo.getName());
        if (newProductInfo.getDescription() != null)
            p.setDescription(newProductInfo.getDescription());
        if (newProductInfo.getPrice() != null)
            p.setPrice(newProductInfo.getPrice());
        productRepository.save(p);
        return p;
    }

    public void deleteProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new IllegalStateException("Product (ID) not found.");
        }
        productRepository.deleteById(id);
    }
}
