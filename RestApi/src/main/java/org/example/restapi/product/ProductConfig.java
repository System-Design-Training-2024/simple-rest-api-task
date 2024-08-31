package org.example.restapi.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class ProductConfig {

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
               Product lol= new Product(
                        "hashem",
                        "SSSS",
                        3333D

                );
      //  productRepository.saveAll(List.of(lol));
        };
    }
}
