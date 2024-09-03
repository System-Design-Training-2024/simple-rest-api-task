package com.example.simple_rest_api.Product;
import jakarta.persistence.*;//import all jakarta lib
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="product")
public class Product {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   @Column(nullable = false)
   private String name;
   
   @Column
   private String description;
   
   @Column(nullable = false)
   private Double price;

   private Timestamp createdAt;

   private Timestamp updatedAt;

   @PrePersist
   protected void onCreate() {
      createdAt = Timestamp.valueOf(LocalDateTime.now());
      updatedAt = Timestamp.valueOf(LocalDateTime.now());
   }

   @PreUpdate
   protected void onUpdate() {
      updatedAt = Timestamp.valueOf(LocalDateTime.now());
   }

   // Getters and setters

   //id
   public Long getId() {
       return id;
   }
   public void setId(Long id) {
       this.id = id;
   }
   //name
   public String getName() {
       return name;
   }
   public void setName(String name) {
       this.name = name;
   }
//descrip
   public String getDescription() {
       return description;
   }
   public void setDescription(String description) {
       this.description = description;
   }
//price
   public Double getPrice() {
       return price;
   }
   public void setPrice(Double price) {
       this.price = price;
   }
//Createdat,updatedat
   public Timestamp getCreatedAt() {
       return createdAt;
   }
   public Timestamp getUpdatedAt() {
       return updatedAt;
   }
}
