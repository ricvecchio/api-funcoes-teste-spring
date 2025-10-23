package com.funcoes.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

/**
 * Cliente de logging genérico utilizado por outros serviços.
 * Fornece métodos convenientes para registrar logs padronizados.
 */
@Slf4j
@Component
public class LogClient {

    /**
     * Log de informação genérico.
     */
    public void info(String service, String action, String message) {
        log.info(format("INFO", service, action, message));
    }

    /**
     * Log de sucesso (operação concluída).
     */
    public void success(String service, String action, String resource, String message) {
        String fullMsg = String.format("Recurso: %s | %s", resource, message);
        log.info(format("SUCCESS", service, action, fullMsg));
    }

    /**
     * Log de erro com mensagem simples.
     */
    public void error(String service, String action, String message) {
        log.error(format("ERROR", service, action, message));
    }

    /**
     * Log de erro com exceção.
     */
    public void error(String service, String action, String message, Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();
        log.error(format("ERROR", service, action, message + " | StackTrace: " + stackTrace));
    }

    /**
     * Monta uma linha padronizada de log.
     */
    private String format(String level, String service, String action, String message) {
        return String.format("[%s] [%s] [%s] [%s] %s",
                LocalDateTime.now(),
                level,
                service,
                action,
                message
        );
    }
}
