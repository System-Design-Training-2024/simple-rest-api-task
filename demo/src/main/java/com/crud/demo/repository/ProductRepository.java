package com.crud.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.crud.demo.table.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

}
