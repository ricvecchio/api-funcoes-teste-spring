package com.funcoes.logging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * ✅ Cliente de log centralizado.
 * Envia logs ao log-service e ao Splunk (modo gratuito compatível).
 */
@Component
public class LogClient {

    @Value("${log.service.url:http://log-service:8083/api/logs}")
    private String logServiceUrl;

    @Value("${splunk.hec.url:}")
    private String splunkUrl;

    @Value("${splunk.hec.token:}")
    private String splunkToken;

    private final RestTemplate rest = new RestTemplate();

    private void sendToLogService(Map<String, Object> body) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            rest.exchange(logServiceUrl, HttpMethod.POST, new HttpEntity<>(body, headers), Void.class);
        } catch (Exception e) {
            System.err.printf("⚠️ Falha ao enviar log para log-service: %s%n", e.getMessage());
        }
    }

    private void sendToSplunk(Map<String, Object> body) {
        if (splunkUrl == null || splunkUrl.isBlank()) return;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Splunk " + splunkToken);
            rest.exchange(splunkUrl, HttpMethod.POST, new HttpEntity<>(Map.of("event", body), headers), Void.class);
        } catch (Exception e) {
            System.err.printf("⚠️ Falha ao enviar log para Splunk: %s%n", e.getMessage());
        }
    }

    private void send(String service, String topic, String level, String message) {
        try {
            String traceId = CorrelationId.getId();
            Map<String, Object> body = Map.of(
                    "traceId", traceId,
                    "service", service,
                    "topic", topic,
                    "level", level,
                    "message", message,
                    "timestamp", LocalDateTime.now().toString()
            );
            sendToLogService(body);
            sendToSplunk(body);
        } catch (Exception e) {
            System.err.printf("⚠️ Erro interno no LogClient: %s%n", e.getMessage());
        }
    }

    public void info(String s, String t, String m)    { send(s, t, "INFO", m); }
    public void warn(String s, String t, String m)    { send(s, t, "WARN", m); }
    public void error(String s, String t, String m)   { send(s, t, "ERROR", m); }
    public void consume(String s, String t, String m) { send(s, t, "CONSUME", m); }
    public void success(String s, String t, String key, String value) {
        send(s, t, "SUCCESS", "Mensagem enviada com sucesso: key=" + key + " value=" + value);
    }
}
