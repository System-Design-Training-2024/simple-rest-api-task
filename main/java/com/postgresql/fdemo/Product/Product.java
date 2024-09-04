package com.postgresql.fdemo.Product;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table (name = "product")
public class Product {

    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @SequenceGenerator(
            name = "Sequence",
            sequenceName = "Sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Sequence"
    )

    private Long Id ;

    private String name ;
    private String description ;
    private Double price ;
    private LocalDateTime created_at ;
    private LocalDateTime updated_at  ;


    public Product() {

    }

    public Product(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Product{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
