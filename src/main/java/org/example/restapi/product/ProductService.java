package org.example.restapi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

        private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(@PathVariable Long id) {
       Optional<Product> product = productRepository.findById(id);
       if(product.isPresent()) {
           return product.get();
       }
       else{
           throw new IllegalStateException(String.format("Product with id %s not found", id));
       }
    }

    public Product createProduct(Product product) {
        Boolean ename=(product.getName()==null);
        Boolean eprice=(product.getPrice()==null);

        if(ename||eprice){
            throw new IllegalArgumentException("Product name and price are required");
        }
        return productRepository.save(product);
    }


    public void  deleteProductById(@PathVariable Long id) {
            Optional<Product> product = productRepository.findById(id);
            if(product.isPresent()) {
                productRepository.delete(product.get());
            }
            else{
                throw new IllegalStateException(String.format("Product with id %s not found", id));
            }
    }


    public Product updateProductbyId( Product product, Long id) {
        Boolean ename=(product.getName()==null);
        Boolean edescription=(product.getDescription()==null);
        Boolean eprice=(product.getPrice()==null);

            Product therealproduct=getProductById(id);
            if(!ename)
                therealproduct.setName(product.getName());
            if(!edescription)
                therealproduct.setDescription(product.getDescription());
            if(!eprice)
                therealproduct.setPrice(product.getPrice());

            therealproduct.set_updated_at(LocalDateTime.now());

            return productRepository.save(therealproduct);

    }
}
