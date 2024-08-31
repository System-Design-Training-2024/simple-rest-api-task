package org.example.restapi.product;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Entity
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    @Column(nullable = false)
    private String Name;
    @Column
    private String description;


    @Column(nullable = false)
    private Double price;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    public Product() {
        created_at=LocalDateTime.now();
    }

    public Product( Long id,String name, String description, Double price) {
        Name = name;
        this.description = description;
        this.price = price;
        Id = id;
        created_at = LocalDateTime.now();
    }

    public Product(String name, String description, Double price) {
        this.Name = name;
        this.description = description;
        this.price = price;
        created_at = LocalDateTime.now();
    }

    public void set_updated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
