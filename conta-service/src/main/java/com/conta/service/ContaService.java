package com.conta.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funcoes.logging.CorrelationId;
import com.funcoes.logging.CorrelationIdFilter;
import com.funcoes.logging.LogClient;
import com.conta.dto.AbrirContaRequest;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * ✅ Regras de negócio de Conta.
 * Publica mensagem Kafka e envia logs ao Log-Service.
 */
@Service
public class ContaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final LogClient log;

    @Value("${conta.aberturas.topic}")
    private String contaAberturasTopic;

    public ContaService(KafkaTemplate<String, String> kafkaTemplate,
                        ObjectMapper objectMapper,
                        LogClient log) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.log = log;
    }

    public void abrirConta(AbrirContaRequest request) {
        try {
            if (request.getCpf() != null) {
                request.setCpf(request.getCpf().replaceAll("\\D", ""));
            }
            String key = request.getCpf();
            String payload = objectMapper.writeValueAsString(request);

            log.info("ContaService", contaAberturasTopic, "Enviando mensagem ao tópico com key=" + key);

            // adiciona traceId no header Kafka
            String traceId = CorrelationId.getId();
            ProducerRecord<String, String> record =
                    new ProducerRecord<>(contaAberturasTopic, key, payload);

            if (traceId != null) {
                record.headers().add(new RecordHeader(
                        CorrelationIdFilter.TRACE_HEADER,
                        traceId.getBytes(StandardCharsets.UTF_8)
                ));
            }

            kafkaTemplate.send(record);
            log.success("ContaService", contaAberturasTopic, key, payload);

        } catch (Exception e) {
            log.error("ContaService", contaAberturasTopic, "Erro ao publicar mensagem Kafka: " + e.getMessage());
            throw new RuntimeException("Erro ao publicar mensagem Kafka", e);
        }
    }
}
