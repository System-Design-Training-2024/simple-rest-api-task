package com.example.productapi.product;

public class ProductTemp {
    private String name;
    private Double price;
    private String description;

    public ProductTemp(String name, Double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public void setProductDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getProductPrice() {
        return price;
    }

    public String getProductDescription() {
        return description;
    }
}
