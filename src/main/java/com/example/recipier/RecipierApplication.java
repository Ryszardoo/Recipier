package com.example.recipier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RecipierApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipierApplication.class, args);
    }
}
