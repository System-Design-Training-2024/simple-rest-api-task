package com.proj.anaas.Product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class ProductConfigration {

    @Bean
    CommandLineRunner runner(ProductReposirory reposirory){
        return args -> {
            Product Apple = new Product("Anas", "HELLO", 20.1);
            Product Samsung = new Product("Karam", "love", 50.2);
            reposirory.saveAll(List.of(Apple, Samsung));
        };
    }
}
