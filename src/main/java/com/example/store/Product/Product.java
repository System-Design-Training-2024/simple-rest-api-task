package com.example.store.Product;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table
public class Product {

    @Id
    @SequenceGenerator(
            name = "Product_Sequence",
            sequenceName = "Product_Sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.UUID,
            generator = "Product_Sequence"
    )
    Long id;
    String name;
    String description;
    Double price;
    Timestamp created_at = Timestamp.valueOf(LocalDateTime.now());
    Timestamp updated_at;

    public Product() {

    }

    public Product(Long id, String name, String description, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
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

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setUpdated_at() {
        this.updated_at = Timestamp.valueOf(LocalDateTime.now());
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    @Override
    public String toString() {
        return "Product {" +
                "id = " + id + "\n" +
                ", name = " + name + "\n" +
                ", description = " + description + "\n" +
                ", price = " + price +
                '}';
    }
}
