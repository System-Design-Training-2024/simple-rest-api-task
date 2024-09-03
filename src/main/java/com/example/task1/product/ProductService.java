package com.example.task1.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService  {
    private final ProductRepo productRepo;


    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

   public void CreateProduct(Product product) {

        product.fix();
        product.fix2();
        productRepo.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product GetByID(Long ID)
    {
        Optional<Product> pp = productRepo.findById(ID);
        if(pp.isEmpty())
        {
            return null;
        }

        return pp.get();

    }

    public void DeleteProductById(Long ID ) throws Exception {
        Optional<Product> pp = productRepo.findById(ID);
        if(pp.isEmpty())
        {
            throw new IllegalArgumentException();
        }

        productRepo.deleteById(ID);

    }
    public Product UpdateProduct(Long ID , String Name , String description , Double price) throws Exception {
        Optional<Product> pp = productRepo.findById(ID);
        if (pp.isEmpty())
            throw new IllegalArgumentException();
        boolean b = true;
        Product product = pp.get();
        if(Name != null)
        {
         product.setName(Name);
            b = false;
        }
        if(description != null)
        {
            product.setDescription(description);
            b = false;
        }

        if(price != null )
        {
            product.setPrice(price);
            b= false;
        }
        //        if(b)
        //        {
        //            throw new IllegalArgumentException();
        //        }
        product.fix2();
        productRepo.save(product);
        return product;
    }

}
