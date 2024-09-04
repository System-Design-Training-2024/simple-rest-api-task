package com.springdata.third.Controller;

import com.springdata.third.Model.Entity.Product;
import com.springdata.third.Model.dto.ProductDto;
import com.springdata.third.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.searchProduct(id));
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getProductS() {
         return ResponseEntity.ok(productService.getAllProducts());
    }



    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
          productService.save(product);
          return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto product, @PathVariable Integer id) {
        return ResponseEntity.ok(productService.update(product, id));
    }
}
