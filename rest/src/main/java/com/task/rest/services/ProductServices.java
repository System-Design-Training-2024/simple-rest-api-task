package com.task.rest.services;

import com.task.rest.DTO.ProductDTO;
import com.task.rest.model.Product;
import com.task.rest.repositories.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServices {
    private final ProductRepository productRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    public ProductServices(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }
    public boolean validateProduct(Product product) {
        if(product.getName() == null || product.getPrice() == null)
            return false;

        return true;
    }
    public List<Product> getAllProducts() {
        return productRepository.findAllProducts();
    }
    public Product getProductById(int id) {
        return productRepository.findProductById(id);
    }
    public boolean validateProductId(int id) {
        if(productRepository.findProductById(id) == null)
            return false;

        return true;
    }

    @Transactional
    public Product updateProduct(int id, ProductDTO newProduct) {
        Product product = getProductById(id);

        if(newProduct.getName() != null || !newProduct.getName().isEmpty())
            product.setName(newProduct.getName());
        if(newProduct.getPrice() != null)
            product.setPrice(newProduct.getPrice());
        if(newProduct.getDescription() != null || !newProduct.getDescription().isEmpty())
            product.setDescription(newProduct.getDescription());

        productRepository.save(product);

        return product;
    }

    @Transactional
    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }
}
