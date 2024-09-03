package com.example.simple_rest_api.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simple_rest_api.Product.Product;
import com.example.simple_rest_api.ProductRepository.ProductRepostiory;

import java.util.List;

@Service
public class ProductService {
  private  ProductRepostiory productRepostiory;

  @Autowired
  public ProductService(ProductRepostiory productRepostiory){
    this.productRepostiory=productRepostiory;
  }
  public List<Product> getALLProducts(){
    return productRepostiory.findAll();
  }
  public Product getProductByID(Long id){
    return productRepostiory.findById(id).orElseThrow(()->new RuntimeException("Product not found!"));
  }
  // public boolean product_Exist(Long id){
  //        return productRepostiory.findById(id).isPresent();
  // }


  //Don't use Transactional annotatin !! what if name is true and price have Illegal argument ---> ensure everything is corrct then save it.
  public Product UpdateProductById(Long id,Product new_product){
    Product product=this.getProductByID(id);
       if (new_product.getName() == null) {
          throw new IllegalArgumentException("Name must be more than 0 char!");
       }
       else{
         product.setName(new_product.getName());
       }
       if(new_product.getDescription()==null){
         throw new IllegalArgumentException("Description must be more than 0 char !");
       }
       else{
        product.setDescription(new_product.getDescription());
       }
       if(new_product.getPrice() == null){
        throw new IllegalArgumentException("Price must not be null value!");
       }
       else if(new_product.getPrice()<=0){
        throw new IllegalArgumentException("Price must be more than 0 !");
       }
       else{
          product.setPrice(new_product.getPrice());
       }
       return productRepostiory.save(product);


  }
  public Product createProduct(Product new_product){
    return productRepostiory.save(new_product);
  }
  public boolean deleteProductByID(Long id){
    if(!productRepostiory.findById(id).isPresent()){
      return false;
    }
    productRepostiory.deleteById(id);
    return true;
  }
}
