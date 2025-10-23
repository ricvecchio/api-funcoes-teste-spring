package com.funcoes.kafka.controller;

import com.funcoes.logging.LogClient;
import com.funcoes.kafka.service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador responsável por receber requisições REST
 * e enviar mensagens para o tópico Kafka "conta-aberturas".
 */
@RestController
@RequestMapping("/api/contas")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaService kafkaService;
    private final LogClient logClient;

    private static final String SERVICE_NAME = "KafkaService";
    private static final String TOPIC_NAME = "conta-aberturas";

    @PostMapping("/publicar")
    public ResponseEntity<String> publicar(@RequestBody String mensagem) {
        logClient.info(SERVICE_NAME, TOPIC_NAME, "Recebida requisição para publicar mensagem.");
        try {
            kafkaService.enviarMensagem(TOPIC_NAME, mensagem);
            logClient.success(SERVICE_NAME, TOPIC_NAME, "PUBLICAR_MENSAGEM", "Mensagem publicada com sucesso no Kafka.");
            return ResponseEntity.ok("Mensagem enviada para o Kafka com sucesso.");
        } catch (Exception e) {
            logClient.error(SERVICE_NAME, TOPIC_NAME, "Falha ao publicar mensagem.", e);
            return ResponseEntity.internalServerError().body("Erro ao enviar: " + e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        logClient.info(SERVICE_NAME, TOPIC_NAME, "Health-check executado.");
        return ResponseEntity.ok("KafkaService operacional.");
    }
}
