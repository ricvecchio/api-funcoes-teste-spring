package com.funcoes.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * ✅ Utilitário de logging centralizado.
 * Envia logs estruturados para o log-service via HTTP.
 */
@Slf4j
public class KafkaLogger {

    private static final String LOG_SERVICE_URL = "http://log-service:8083/api/logs";
    private static final RestTemplate restTemplate = new RestTemplate();

    private static void send(String service, String topic, String level, String message, String traceId) {
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("traceId", traceId);
            body.put("service", service);
            body.put("topic", topic);
            body.put("level", level);
            body.put("message", message);
            body.put("timestamp", LocalDateTime.now());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(LOG_SERVICE_URL, entity, Void.class);

        } catch (Exception e) {
            log.error("❌ Falha ao enviar log para o LogService: {}", e.getMessage());
        }
    }

    public static void info(String service, String topic, String message) {
        send(service, topic, "INFO", message, null);
    }

    public static void success(String service, String topic, String message) {
        send(service, topic, "SUCCESS", message, null);
    }

    public static void warn(String service, String topic, String message) {
        send(service, topic, "WARN", message, null);
    }

    public static void error(String service, String topic, String message, Exception e) {
        send(service, topic, "ERROR", message + ": " + e.getMessage(), null);
    }

    public static void consume(String service, String topic, String message) {
        send(service, topic, "CONSUME", message, null);
    }
}
