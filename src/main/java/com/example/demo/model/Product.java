package com.example.demo.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false, updatable = false)
    private Double price;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public Product() {
    }

    // Private constructor to prevent direct instantiation
    private Product(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Timestamp.from(Instant.now());
        updatedAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Timestamp.from(Instant.now());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is required and must contains at least 1 character");
        }
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
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Price is required and must be greater than 0");
        }
        this.price = price;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    // Static nested Builder class
    public static class Builder {
        private String name;
        private String description;
        private Double price;

        // Constructor requiring the name and price parameters
        public Builder(String name, Double price) {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Name is required and must contains at least 1 character");
            }
            if (price == null || price <= 0) {
                throw new IllegalArgumentException("Price is required and must be greater than 0");
            }
            this.name = name;
            this.price = price;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
