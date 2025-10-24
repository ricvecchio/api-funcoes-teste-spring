package com.funcoes.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.funcoes.log")
public class LogServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogServiceApplication.class, args);
    }
}
