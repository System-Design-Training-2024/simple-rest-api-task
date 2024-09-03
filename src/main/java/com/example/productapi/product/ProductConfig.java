package com.example.productapi.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class ProductConfig {

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository repository) {
        return args -> {
            Product laptop = new Product(
                    "Laptop",
                    "High-performance laptop with 16GB RAM and 512GB SSD",
                    BigDecimal.valueOf(999.99)
            );

            Product smartphone = new Product(
                    "Smartphone",
                    "Latest model with 128GB storage and 5G support",
                    BigDecimal.valueOf(699.99)
            );

            Product headphones = new Product(
                    "Headphones",
                    "Noise-cancelling wireless headphones",
                    BigDecimal.valueOf(199.99)
            );

            Product smartwatch = new Product(
                    "Smartwatch",
                    "Smartwatch with fitness tracking and notifications",
                    BigDecimal.valueOf(149.99)
            );

            Product tablet = new Product(
                    "Tablet",
                    "10-inch tablet with high-resolution display and 64GB storage",
                    BigDecimal.valueOf(349.99)
            );

            repository.save(laptop);
            repository.save(smartphone);
            repository.save(headphones);
            repository.save(smartwatch);
            repository.save(tablet);
        };
    }
}
