package com.funcoes.kafka.producer;

import com.funcoes.logging.LogClient;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Responsável por enviar mensagens para o Kafka.
 */
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final LogClient logClient;

    private static final String SERVICE_NAME = "KafkaService";

    public void enviar(String topic, String mensagem) {
        try {
            kafkaTemplate.send(topic, mensagem);
            logClient.success(SERVICE_NAME, topic, "PRODUCAO_MENSAGEM", "Mensagem enviada com sucesso: " + mensagem);
        } catch (Exception e) {
            logClient.error(SERVICE_NAME, topic, "Erro ao enviar mensagem Kafka.", e);
            throw e;
        }
    }

}
