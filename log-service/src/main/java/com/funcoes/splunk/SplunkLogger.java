package com.funcoes.splunk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * ✅ Envia logs para o Splunk HEC (HTTP Event Collector)
 * Compatível com o plano gratuito e modo local.
 */
@Component
public class SplunkLogger {

    private static final Logger log = LoggerFactory.getLogger(SplunkLogger.class);

    @Value("${splunk.hec.url:}")
    private String splunkUrl;

    @Value("${splunk.hec.token:}")
    private String splunkToken;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendLog(String source, String message, String level) {
        if (splunkUrl == null || splunkUrl.isBlank() || splunkToken == null || splunkToken.isBlank()) {
            log.debug("Splunk HEC desabilitado (sem URL ou token configurado).");
            return;
        }

        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("event", Map.of("source", source, "level", level, "message", message));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Splunk " + splunkToken);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            restTemplate.exchange(splunkUrl, HttpMethod.POST, request, String.class);
        } catch (Exception e) {
            log.error("Erro ao enviar log para o Splunk: {}", e.getMessage());
        }
    }
}
