package com.funcoes.kafka.service;

import com.funcoes.logging.LogClient;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * Serviço responsável pelo envio de mensagens ao Kafka.
 * Inclui timeout controlado e logging detalhado.
 */
@Service
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final LogClient logClient;

    private static final String SERVICE_NAME = "KafkaService";

    /**
     * Envia mensagem ao Kafka de forma síncrona com timeout (resiliente).
     *
     * @param topico   Nome do tópico Kafka.
     * @param mensagem Conteúdo JSON a ser enviado.
     * @throws Exception Caso o envio falhe ou exceda o tempo limite.
     */
    public RecordMetadata enviarMensagemComTimeout(String topico, String mensagem) throws Exception {
        logClient.info(SERVICE_NAME, topico,
                "⏳ Iniciando envio de mensagem para o Kafka às " + Instant.now());

        try {
            // Envio síncrono com timeout de 5 segundos
            RecordMetadata metadata = kafkaTemplate.send(topico, mensagem)
                    .get(5, TimeUnit.SECONDS)
                    .getRecordMetadata();

            logClient.success(SERVICE_NAME, topico, "MENSAGEM_ENVIADA",
                    "✅ Mensagem enviada com sucesso para o tópico Kafka. Offset=" + metadata.offset());

            return metadata;
        } catch (Exception e) {
            logClient.error(SERVICE_NAME, topico,
                    "❌ Falha ao enviar mensagem ao Kafka: " + e.getMessage(), e);

            // Repropaga exceção para o controller tratar (sem travar o thread pool)
            throw e;
        }
    }

    /**
     * Envia mensagem de forma assíncrona (sem esperar resposta do broker).
     * Utilizado em cenários onde falhas podem ser tratadas fora da requisição HTTP.
     */
    public void enviarMensagem(String topico, String mensagem) {
        try {
            kafkaTemplate.send(topico, mensagem);
            logClient.info(SERVICE_NAME, topico,
                    "📤 Mensagem enviada de forma assíncrona ao tópico Kafka.");
        } catch (Exception e) {
            logClient.error(SERVICE_NAME, topico,
                    "⚠️ Erro no envio assíncrono ao Kafka: " + e.getMessage(), e);
        }
    }
}
