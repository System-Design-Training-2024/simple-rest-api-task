package com.proj.anaas.Product;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table
public class Product {
    @Id
    @SequenceGenerator(
            name= "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_sequence"
    )
    private Long id;
    private String name;
    private String description;
    private double price;
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creat_at;
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime update_at;

    public Product(){

    }
    public Product(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(Long ID, String name, String description, double price) {
        this.id = ID;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getID() {
        return id;
    }

    public void setID(Long ID) {
        this.id = ID;
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

    public LocalDateTime getCreat_at() {
        return creat_at;
    }

    public LocalDateTime getUpdate_at() {
        return update_at;
    }


    @Override
    public String toString() {
        return "Product{" +
                "ID=" + id +
                ", Name='" + name + '\'' +
                ", Description='" + description + '\'' +
                ", Price=" + price +
                ", creat_at=" + creat_at +
                ", update_at=" + update_at +
                '}';
    }
}
