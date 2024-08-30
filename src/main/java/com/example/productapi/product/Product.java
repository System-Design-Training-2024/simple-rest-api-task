package com.example.productapi.product;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String description;
    private Double price;

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
        this.createdAt = LocalDateTime.now();
        setUpdateTime();
    }

    private void setUpdateTime() {
        updatedAt = LocalDateTime.now();
    }

    public String getUpdateTime() {
        if (updatedAt == null) {
            updatedAt = createdAt;
        }
        return updatedAt.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public String getCreationTime() {
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
        return "\n\tproduct {" +
                "\r\n\t\tid= " + getId() +
                ",\r\n\t\tcreatedAt= " + getCreationTime() +
                ",\r\n\t\tupdatedAt= " + getUpdateTime() +
                ",\r\n\t\tname= '" + getName() +
                "',\r\n\t\tprice= " + getPrice() +
                ",\r\n\t\tdescription= '" + getDescription() + "'\r\n\t}";
    }

}
