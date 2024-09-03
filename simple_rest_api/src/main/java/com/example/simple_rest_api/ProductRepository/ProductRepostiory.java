package com.example.simple_rest_api.ProductRepository;

import org.springframework.stereotype.Repository;

import com.example.simple_rest_api.Product.Product;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface ProductRepostiory extends JpaRepository<Product,Long> {
}