package com.proj.anaas.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductReposirory productReposirory;
    @Autowired
    public ProductService(ProductReposirory productReposirory) {
        this.productReposirory = productReposirory;
    }

    public List<Product> GetProduct(){
        return productReposirory.findAll();
    }
    public Optional<Product> getProductById(Long id) {
        Optional<Product> prod = productReposirory.findById(id);
        if(prod.isPresent()){
            return prod;
        }else
            throw new IllegalStateException("The Product does not exist");
    }
    public Product NewProduct(Product product) {
        if(product.getName() != null && product.getName().length() > 0 && product.getDescription() != null && product.getDescription().length() > 0 && product.getPrice() > 0){
            return productReposirory.save(product);
        }else{
            throw new IllegalStateException("The Product name and description and price are required");
        }
    }
    public void DeleteProduct(Long id) {
        boolean exists = productReposirory.existsById(id);
        if(exists) {
            productReposirory.deleteById(id);
        }else{
            throw new IllegalStateException("The Product with id = " + id + " does not exist");
        }
    }
    public Product UpdateProduct(Long id, Product product){
        boolean exists = productReposirory.existsById(id);
        if(!exists) {
            throw new IllegalStateException("The Product with id = " + id + " does not exist");
        }else{
            Product prod = productReposirory.findById(id).orElse(null);
            if(product.getName() != null && product.getName().length() > 0) {
                prod.setName(product.getName());
            }
            else{
                throw new IllegalStateException("Name can not be Null");
            }
            if(product.getDescription() != null && product.getDescription().length() > 0) {
                prod.setDescription(product.getDescription());
            }else{
                throw new IllegalStateException("Description can not be Null");
            }
            if(product.getPrice() != 0) {
                prod.setPrice(product.getPrice());
            }else{
                throw new IllegalStateException("Price is unaccepted");
            }
            return productReposirory.save(prod);
        }
    }
}
