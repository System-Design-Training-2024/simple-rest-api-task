package com.task.rest.repositories;

import com.task.rest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * FROM products WHERE id = :id",
            nativeQuery = true)
    Product findProductById(int id);
    @Query(value = "SELECT * FROM products",
            nativeQuery = true)
    List<Product> findAllProducts();
    @Modifying(flushAutomatically = true)
    @Query("UPDATE Product p SET p.name = :name, p.price = :price, p.description = :description WHERE p.id = :id")
    void updateProductById(@Param("id") int id, @Param("name") String name, @Param("price") BigDecimal price, @Param("description") String description);
}
