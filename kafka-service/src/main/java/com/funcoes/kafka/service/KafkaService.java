package com.funcoes.kafka.service;

import com.funcoes.logging.LogClient;
import com.funcoes.kafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Camada de serviço que encapsula o envio de mensagens Kafka.
 */
@Service
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaProducer kafkaProducer;
    private final LogClient logClient;

    private static final String SERVICE_NAME = "KafkaService";

    public void enviarMensagem(String topic, String mensagem) {
        logClient.info(SERVICE_NAME, topic, "Iniciando envio de mensagem para Kafka...");
        kafkaProducer.enviar(topic, mensagem);
        logClient.info(SERVICE_NAME, topic, "Mensagem publicada com sucesso.");
    }
}
