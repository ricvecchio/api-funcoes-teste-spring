package com.funcoes.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funcoes.logging.CorrelationId;
import com.funcoes.logging.CorrelationIdFilter;
import com.funcoes.logging.LogClient;
import com.funcoes.model.*;
import com.funcoes.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class ContaConsumer {

    private final ObjectMapper objectMapper;
    private final ClienteRepository clienteRepository;
    private final ContaRepository contaRepository;
    private final LogClient log;

    @Value("${conta.aberturas.topic}")
    private String topic;

    @KafkaListener(topics = "${conta.aberturas.topic}", groupId = "kafka-service")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            String key = record.key();

            // lê traceId do header Kafka e coloca no contexto
            Header h = record.headers().lastHeader(CorrelationIdFilter.TRACE_HEADER);
            if (h != null) {
                String traceId = new String(h.value(), StandardCharsets.UTF_8);
                CorrelationId.setId(traceId);
            }

            log.consume("KafkaService", topic, "Mensagem recebida: key=" + key + " value=" + message);

            AbrirContaRequest request = objectMapper.readValue(message, AbrirContaRequest.class);
            String cpf = request.getCpf().replaceAll("\\D", "");

            Cliente cliente = clienteRepository.findByCpf(cpf)
                    .orElseGet(() -> {
                        Cliente novo = new Cliente();
                        novo.setNome(request.getNomeCliente());
                        novo.setCpf(cpf);
                        return clienteRepository.save(novo);
                    });

            Conta conta = new Conta();
            conta.setTipo(request.getTipoConta());
            conta.setStatus(StatusConta.ATIVA);
            conta.setCliente(cliente);
            contaRepository.save(conta);

            log.success("KafkaService", topic, cpf, "Conta criada com sucesso para " + cliente.getNome());

        } catch (Exception e) {
            log.error("KafkaService", topic, "Erro ao processar mensagem Kafka: " + e.getMessage());
        } finally {
            CorrelationId.clear();
        }
    }
}
