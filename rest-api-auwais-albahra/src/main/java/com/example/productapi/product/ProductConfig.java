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
            Product kidney = new Product(
                    "kidney",
                    "a very good working one i think soo",
                    BigDecimal.valueOf(1.05)
            );


            repository.save(kidney);

        };
    }
}
