package com.funcoes.consumer;

import com.funcoes.logging.LogClient;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumidor Kafka para o tópico "conta-aberturas".
 * Apenas registra logs com as mensagens recebidas.
 */
@Component
@RequiredArgsConstructor
public class ContaConsumer {

    private final LogClient logClient;
    private static final String SERVICE_NAME = "KafkaService";

    @KafkaListener(topics = "conta-aberturas", groupId = "conta-group")
    public void consumir(ConsumerRecord<String, String> record) {
        String mensagem = record.value();
        try {
            logClient.info(SERVICE_NAME, "conta-aberturas",
                    "Mensagem consumida do Kafka: " + mensagem);
        } catch (Exception e) {
            logClient.error(SERVICE_NAME, "conta-aberturas",
                    "Erro ao processar mensagem Kafka.", e);
        }
    }
}
