package com.funcoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ContaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContaServiceApplication.class, args);
    }
}