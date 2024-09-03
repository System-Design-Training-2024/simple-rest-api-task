package com.proj.anaas;

import com.proj.anaas.Product.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@SpringBootApplication
@RestController
public class AnaasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnaasApplication.class, args);
	}

}
