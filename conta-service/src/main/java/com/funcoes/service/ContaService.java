package com.funcoes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funcoes.model.AbrirContaRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por processar a abertura de contas e enviar os dados
 * para o tópico Kafka definido em application.yml ou .env.
 * <p>
 * Esta classe aplica boas práticas como:
 * - Limpeza do CPF (removendo caracteres não numéricos);
 * - Logging informativo;
 * - Tratamento robusto de exceções;
 * - Injeção de dependências via construtor.
 */
@Service
public class ContaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    // Nome do tópico definido nas configurações
    @Value("${conta.aberturas.topic}")
    private String contaAberturasTopic;

    public ContaService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Publica uma solicitação de abertura de conta no tópico Kafka.
     *
     * @param request objeto contendo os dados da conta a ser aberta
     */
    public void abrirConta(AbrirContaRequest request) {
        try {
            // 🧹 Limpa o CPF antes de enviar (mantém apenas números)
            if (request.getCpf() != null) {
                request.setCpf(request.getCpf().replaceAll("\\D", ""));
            }

            // Converte o objeto para JSON
            String payload = objectMapper.writeValueAsString(request);

            // Publica no tópico Kafka
            kafkaTemplate.send(contaAberturasTopic, request.getCpf(), payload);

            System.out.printf("✅ [ContaService] Mensagem enviada ao tópico [%s]: key=%s value=%s%n",
                    contaAberturasTopic, request.getCpf(), payload);

        } catch (Exception e) {
            System.err.printf("❌ [ContaService] Erro ao publicar mensagem Kafka: %s%n", e.getMessage());
            throw new RuntimeException("Erro ao publicar mensagem Kafka", e);
        }
    }
}