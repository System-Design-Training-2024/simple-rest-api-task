package com.example.simple_rest_api.ProductController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.simple_rest_api.Product.Product;
import com.example.simple_rest_api.ProductService.ProductService;

import java.util.List;
@RestController
@RequestMapping("/products")
public class ProductController {
   
    private ProductService productService;
    
    @Autowired
    public ProductController(ProductService productService){
        this.productService=productService;
    }
    
    @GetMapping
    public ResponseEntity<List<Product>> getALLProducts(){
          List<Product> products=productService.getALLProducts();
          return ResponseEntity.ok(products);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductByID(@PathVariable Long id){
        Product product=productService.getProductByID(id);

        if(product==null){
            return ResponseEntity.notFound().build();
        }
        
            return ResponseEntity.ok(product);
        
    }
    @PutMapping("/{id}") //update
    public ResponseEntity<?> UpdateProductById(@PathVariable Long id,@RequestBody Product new_product){
            try{
                Product product=productService.UpdateProductById(id,new_product);
                return ResponseEntity.ok(product);
            }
            catch (IllegalArgumentException exp){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMessage());
            }
            catch( Exception exp){
                 // ErrorHandling errorResponse = new ErrorHandling(HttpStatus.NOT_FOUND.value(), exp.getMessage());
                  
                  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exp.getMessage());   //this status code is not correct in all cases (IllegalArgumentException)
                   }
    }
    @PostMapping
    public ResponseEntity<Product> makeProduct(@RequestBody Product new_product){
        Product createProduct=productService.createProduct(new_product);
        return new ResponseEntity<>(createProduct,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        boolean isRmoved= productService.deleteProductByID(id);
        if(isRmoved){
            ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
        
                

