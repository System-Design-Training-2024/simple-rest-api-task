package com.proj.anaas.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.OptionalInt;

@Repository
public interface ProductReposirory extends JpaRepository<Product, Long> {
    Optional<Product> findProductByname(String name);
}
