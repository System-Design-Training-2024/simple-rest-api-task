package com.example.task1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping
public class Task1Application {

    public static void main(String[] args) {
        SpringApplication.run(Task1Application.class, args);
    }
    @GetMapping("/products")
    public String khara()
    {
        return "hi this is code";
    }



}
