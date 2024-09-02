package com.crud.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crud.demo.repository.ProductRepository;
import com.crud.demo.table.Product;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts(){
        return  productRepository.findAll();
    }

    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }

    public Boolean productExists(Long id){
        Optional<Product> existingProductOpt = productRepository.findById(id);
        return existingProductOpt.isPresent();
    }
    public Boolean checkProductConstraints(Product product){
        return !product.getName().isEmpty() && !product.getDescription().isEmpty() && (product.getPrice()>0);
    }
    public void updateProductById(Long id, Product productDetails){
            Product product = productRepository.findById(id).get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            productRepository.save(product);
    }
}