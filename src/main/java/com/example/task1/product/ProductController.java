package com.example.task1.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService ps;
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService ps, ProductService productService) {
        this.ps = ps;
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product p) {
        ps.CreateProduct(p);
        return  ResponseEntity.ok(p);
    }
    @GetMapping
    public ResponseEntity<List<Product>> GetAllProduct(){
        return ResponseEntity.ok(ps.getAllProducts());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> GetProductByid(@PathVariable Long id){
        try
        {
            Product pp= ps.GetByID(id);
            return ResponseEntity.ok(pp);
        }catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> UpdateProduct(@PathVariable Long id, @RequestBody UpdateParameter here){
        try
        {
        Product ppp= ps.UpdateProduct(id ,here.getNname() , here.getNdesc() , here.getNprice());
        return ResponseEntity.ok(ppp);
        }
        catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> DeleteProduct(@PathVariable Long id){
        try
        {
            ps.DeleteProductById(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }
    }

}
