package com.postgresql.ProductServer;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class Product {
    @Id
    @SequenceGenerator(
            name = "ProductSequence",
            sequenceName = "ProductSequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ProductSequence"
    )
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Date created_at;
    private Date updated_at;

    public Product() {
    }

    public Product(Double price, String description, String name) {
        this.price = price;
        this.description = description;
        this.name = name;
        this.created_at = new Date();
        this.updated_at = new Date();
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
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
