package com.example.productapi.product;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table
public class Product {
    @Column
    private LocalDateTime createdAt;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double price;
    @Column
    private LocalDateTime updatedAt;
    @Column
    private String description;

    public Product() {
        createdAt = LocalDateTime.now();
        setUpdateTime();
    }

    public Product(String name,
                   Double price,
                   String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    private void setUpdateTime() {
        updatedAt = LocalDateTime.now();
    }

    public String getUpdateTime() {
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        return updatedAt.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public String getCreationTime() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        return createdAt.format(DateTimeFormatter.ISO_DATE_TIME);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setUpdateTime();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
        setUpdateTime();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        setUpdateTime();
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "\nproduct {" +
                " id= " + getId() +
                ", createdAt= " + getCreationTime() +
                ", updatedAt= " + getUpdateTime() +
                ", name= '" + getName() +
                "', price= " + getPrice() +
                ", description= '" + getDescription() + "'}";
    }

}
