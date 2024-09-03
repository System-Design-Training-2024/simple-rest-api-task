package com.postgresql.fdemo;

import com.postgresql.fdemo.Product.Product;
import com.postgresql.fdemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FdemoApplication  {

	public static void main(String[] args) {
		SpringApplication.run(FdemoApplication.class, args);
	}


}
