package com.example.store.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(@RequestBody Product product) {
        return this.productRepository.save(product);
    }

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product to_find = this.productRepository.findById(id).orElse(null);

        if (to_find != null)
            return ResponseEntity.ok(to_find);
        return ResponseEntity.notFound().build(); // 404
    }

    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        Product toUpdate = this.productRepository.findById(id).orElse(null);

        if (toUpdate != null) {
            if (product.getName() != null){
                toUpdate.setName(product.getName());
            }

            if (product.getDescription() != null){
                toUpdate.setDescription(product.getDescription());
            }

            if (product.getPrice() != null){
                toUpdate.setPrice(product.getPrice());
            }

            toUpdate.setUpdated_at();
            this.productRepository.save(toUpdate);

            return ResponseEntity.ok(toUpdate);
        }
        return ResponseEntity.notFound().build(); // 404
    }

    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Product product = this.productRepository.findById(id).orElse(null);
        if (product != null) {
            this.productRepository.delete(product);
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404
    }
}
