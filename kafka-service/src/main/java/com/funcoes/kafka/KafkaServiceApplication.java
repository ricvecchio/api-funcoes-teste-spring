package com.funcoes.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.funcoes.kafka",
        "com.funcoes.logging"  // garante que LogClient e LogConfig sejam encontrados
})
public class KafkaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaServiceApplication.class, args);
    }
}
