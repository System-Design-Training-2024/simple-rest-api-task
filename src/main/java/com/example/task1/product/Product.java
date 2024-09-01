package com.example.task1.product;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDateTime;


@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;
    @Column
    private String description;
    @Column
    private LocalDateTime created_at;
    @Column
    private LocalDateTime updated_at;

    public Product()
    {
        created_at = LocalDateTime.now();
        updated_at = created_at;

    }
    public void fix()
    {
        created_at = LocalDateTime.now();
    }
    public void fix2()
    {
        updated_at = LocalDateTime.now();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

   // @Override
//    public String toString() {
//        return "Product{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", price=" + price +
//                ", description='" + description + '\'' +
//                ", created_at=" + created_at +
//                ", updated_at=" + updated_at +
//                '}';
//    }
}
