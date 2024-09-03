package com.example.store.Product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

// Posted when running the server

@Configuration
public class ProductConfig {

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository repository) {
        return args -> {
            Product water = new Product(
                    "Water",
                    "Bottled water",
                    0.5
            );

            Product orangeJuice = new Product(
                    "Orange Juice",
                    "Natural fresh orange juice",
                    1.0
            );

            repository.saveAll(List.of(water, orangeJuice));
        };
    }
}
