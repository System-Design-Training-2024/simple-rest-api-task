package com.task.caraxes;

import jakarta.transaction.Transactional;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();

    }
    @Transactional
    public Product getProductById(UUID productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " does not exist"));
    }

    public void createProduct(Product product) {
        Optional<Product> productById = productRepository.findById(product.getId());
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product with id " + productId + " does not exist");
        }
        productRepository.deleteById(productId);
    }//

    @Transactional
    public Product updateProduct(UUID productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " does not exist"));

        if (updatedProduct.getName() != null)
            existingProduct.setName(updatedProduct.getName());

        if (updatedProduct.getDescription() != null)
            existingProduct.setDescription(updatedProduct.getDescription());

        if (updatedProduct.getPrice() != 0)
            existingProduct.setPrice(updatedProduct.getPrice());


        return productRepository.save(existingProduct);
    }
}
