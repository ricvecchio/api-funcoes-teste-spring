package com.kafka.util;

import com.funcoes.logging.LogClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Classe utilitária central de logs para o módulo kafka-service.
 * Usa o LogClient da logging-lib para registrar logs padronizados.
 */
@Component
@RequiredArgsConstructor
public class KafkaLogger {

    private final LogClient logClient;

    private static final String SERVICE_NAME = "kafka-service";

    /**
     * Loga uma mensagem informativa.
     */
    public void info(String action, String message) {
        logClient.info(SERVICE_NAME, action, message);
    }

    /**
     * Loga sucesso de uma operação.
     */
    public void success(String action, String resource, String message) {
        logClient.success(SERVICE_NAME, action, resource, message);
    }

    /**
     * Loga erro simples.
     */
    public void error(String action, String message) {
        logClient.error(SERVICE_NAME, action, message);
    }

    /**
     * Loga erro com exceção.
     */
    public void error(String action, String message, Exception e) {
        logClient.error(SERVICE_NAME, action, message, e);
    }
}
