package com.funcoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.funcoes")
public class ContaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContaServiceApplication.class, args);
    }
}
