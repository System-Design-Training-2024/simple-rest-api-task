package com.springdata.third.repositroy;

import com.springdata.third.Model.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Integer> {

}
