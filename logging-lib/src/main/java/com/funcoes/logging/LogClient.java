package com.funcoes.logging;

import java.time.Instant;
import org.springframework.stereotype.Component;


/**
 * Cliente centralizado para envio de logs estruturados.
 * Pode futuramente enviar logs para o kafka-service ou log-service.
 */
@Component
public class LogClient {

    public void info(String service, String topic, String action, String message) {
        LogEntry entry = LogEntry.builder()
                .serviceName(service)
                .topicName(topic)
                .action(action)
                .level("INFO")
                .message(message)
                .timestamp(Instant.now().toString())
                .build();

        send(entry);
    }

    public void success(String service, String topic, String action, String message) {
        LogEntry entry = LogEntry.builder()
                .serviceName(service)
                .topicName(topic)
                .action(action)
                .level("SUCCESS")
                .message(message)
                .timestamp(Instant.now().toString())
                .build();

        send(entry);
    }

    public void error(String service, String topic, String message) {
        LogEntry entry = LogEntry.builder()
                .serviceName(service)
                .topicName(topic)
                .action("ERROR")
                .level("ERROR")
                .message(message)
                .timestamp(Instant.now().toString())
                .build();

        send(entry);
    }

    public void error(String service, String topic, String message, Exception e) {
        String fullMessage = message;
        if (e != null) {
            fullMessage += " | Exception: " + e.getClass().getSimpleName() + " - " + e.getMessage();
        }

        LogEntry entry = LogEntry.builder()
                .serviceName(service)
                .topicName(topic)
                .action("ERROR")
                .level("ERROR")
                .message(fullMessage)
                .timestamp(java.time.Instant.now().toString())
                .build();

        send(entry);
    }

    private void send(LogEntry entry) {
        // Por enquanto apenas imprime no console
        System.out.println("[LOG] " + entry);
    }

    // Sobrecarga compatível com 3 parâmetros (sem 'action')
    public void info(String service, String topic, String message) {
        LogEntry entry = LogEntry.builder()
                .serviceName(service)
                .topicName(topic)
                .action("N/A")
                .level("INFO")
                .message(message)
                .timestamp(java.time.Instant.now().toString())
                .build();

        send(entry);
    }

}
