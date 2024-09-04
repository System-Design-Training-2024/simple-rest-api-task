package com.springdata.third.service;

import com.springdata.third.Model.Entity.Product;
import com.springdata.third.Model.dto.ProductDto;
import com.springdata.third.repositroy.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public ProductDto searchProduct(Integer id) {
        Optional<Product> prod = productRepo.findById(id);
        if (prod.isPresent()) {
            return ProductDto.fromEntityToDto(prod.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }

    public Product save(Product prod) {
        if (prod.getName()==null || prod.getPrice()==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name and price are required");
        }
        return productRepo.save(prod);

    }

    public ProductDto update( ProductDto prod, Integer id) {
        Optional<Product> p = productRepo.findById(id);
        if (p.isPresent())
        {
            Product prod1 = p.get();
            if (prod.getName()!=null ) {
                prod1.setName(prod.getName());
            }
            if (prod.getPrice()!=null ) {
                prod1.setPrice(prod.getPrice());
            }
            if(prod.getDescription()!=null )
                prod1.setDescription(prod.getDescription());

            return ProductDto.fromEntityToDto(productRepo.save(prod1));

        }
       throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }
    public List<ProductDto> getAllProducts() {
        List<Product> prods = productRepo.findAll();
        List<ProductDto> prodDto = new ArrayList<>();

        for (Product prod : prods) {
            ProductDto dto = ProductDto.fromEntityToDto(prod);
            prodDto.add(dto);
        }

        return prodDto;


    }

    public void delete(Integer id) {
        if (!productRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productRepo.deleteById(id);
    }
}
