package com.funcoes.util;

import com.funcoes.logging.LogClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Utilitário simples para registrar logs específicos de Kafka
 * (pode ser usado em produtores ou consumidores).
 */
@Component
@RequiredArgsConstructor
public class KafkaLogger {

    private final LogClient logClient;

    public void logInfo(String origem, String mensagem) {
        logClient.info("KafkaService", origem, mensagem);
    }

    public void logError(String origem, String mensagem, Exception e) {
        logClient.error("KafkaService", origem, mensagem, e);
    }
}
