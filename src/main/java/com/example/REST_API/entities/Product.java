package com.example.REST_API.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
public class Product {
    @Id
    @SequenceGenerator(name = "sqGen1", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqGen1")
    private int id;
    private String name;
    private String description;
    private double price;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Product() {
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    public Product(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    public int getId() {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
