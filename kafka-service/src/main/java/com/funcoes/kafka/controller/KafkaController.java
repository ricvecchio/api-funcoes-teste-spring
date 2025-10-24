package com.funcoes.kafka.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funcoes.kafka.dto.MensagemKafkaRequest;
import com.funcoes.kafka.service.KafkaService;
import com.funcoes.logging.LogClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Controller responsável por receber mensagens externas e publicá-las no Kafka.
 * Também fornece um endpoint simples de health-check.
 */
@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaService kafkaService;
    private final LogClient logClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String SERVICE_NAME = "KafkaService";
    private static final String TOPIC_NAME = "conta-aberturas";

    /**
     * Endpoint responsável por receber uma requisição JSON representando uma mensagem
     * de abertura de conta e publicá-la no tópico Kafka configurado.
     *
     * Exemplo de requisição:
     * {
     *   "idCliente": 123,
     *   "nomeCliente": "Ricardo Del Vecchio",
     *   "tipoConta": "CORRENTE",
     *   "saldoInicial": 1500.00,
     *   "dataAbertura": "2025-10-24T12:00:00Z"
     * }
     */
    @PostMapping("/mensagens")
    public ResponseEntity<Map<String, Object>> publicarMensagem(@Valid @RequestBody MensagemKafkaRequest request) {
        Map<String, Object> response = new HashMap<>();
        Instant timestamp = Instant.now();

        logClient.info(SERVICE_NAME, TOPIC_NAME,
                "📩 Recebida requisição para publicar mensagem no Kafka.");

        try {
            // Serializa o objeto para JSON antes de enviar ao Kafka
            String mensagemJson = objectMapper.writeValueAsString(request);

            // Envia mensagem de forma síncrona com timeout controlado
            kafkaService.enviarMensagemComTimeout(TOPIC_NAME, mensagemJson);

            logClient.success(SERVICE_NAME, TOPIC_NAME, "PUBLICAR_MENSAGEM",
                    "✅ Mensagem publicada com sucesso no tópico Kafka.");

            response.put("status", "OK");
            response.put("mensagem", "Mensagem enviada para o Kafka com sucesso.");
            response.put("timestamp", timestamp.toString());
            response.put("topico", TOPIC_NAME);

            return ResponseEntity.accepted().body(response);

        } catch (Exception e) {
            logClient.error(SERVICE_NAME, TOPIC_NAME,
                    "❌ Falha ao publicar mensagem no Kafka: " + e.getMessage(), e);

            response.put("status", "ERRO");
            response.put("mensagem", "Falha ao enviar mensagem: " + e.getMessage());
            response.put("timestamp", timestamp.toString());
            response.put("topico", TOPIC_NAME);

            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Endpoint simples de verificação de status do serviço Kafka.
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status() {
        Map<String, Object> response = new HashMap<>();
        Instant timestamp = Instant.now();

        logClient.info(SERVICE_NAME, TOPIC_NAME, "🔍 Health-check executado.");

        response.put("status", "UP");
        response.put("servico", SERVICE_NAME);
        response.put("timestamp", timestamp.toString());

        return ResponseEntity.ok(response);
    }
}
