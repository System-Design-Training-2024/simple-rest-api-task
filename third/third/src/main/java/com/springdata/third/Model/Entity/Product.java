package com.springdata.third.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String Name;
    @Column
    private String description;
    @Column(nullable = false)
    private Double price;
    @CreatedDate
    @Column
    private LocalDateTime created_at;
    @LastModifiedDate
    @Column
    private LocalDateTime updated_at;

    public Product() {
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    public void onUpdate() {
        this.updated_at = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.Name = name;
    }
}
