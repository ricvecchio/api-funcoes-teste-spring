package com.funcoes.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funcoes.model.AbrirContaRequest;
import com.funcoes.model.Cliente;
import com.funcoes.model.Conta;
import com.funcoes.model.StatusConta;
import com.funcoes.repository.ClienteRepository;
import com.funcoes.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumer responsável por processar mensagens Kafka de abertura de contas.
 * Simula o fluxo real de criação de cliente e conta, e permite testes de falha.
 */
@Component
@RequiredArgsConstructor
public class ContaConsumer {

    private final ObjectMapper objectMapper;
    private final ClienteRepository clienteRepository;
    private final ContaRepository contaRepository;

    /**
     * Consome mensagens do tópico Kafka de abertura de contas.
     * Caso o CPF seja "00000000000", simula uma falha de processamento para teste de retry.
     */
    @KafkaListener(topics = "${conta.aberturas.topic}", groupId = "kafka-service")
    public void consume(String message) {
        try {
            // 🎯 Converte o payload JSON recebido em objeto de requisição
            AbrirContaRequest request = objectMapper.readValue(message, AbrirContaRequest.class);

            // 🧹 Normaliza o CPF (mantém apenas números)
            String cpfLimpo = request.getCpf().replaceAll("\\D", "");

            // ⚠️ Cenário 3: Simulação de falha no processamento (teste de retry)
            if ("00000000000".equals(cpfLimpo)) {
                throw new RuntimeException("Simulando falha no processamento para o CPF 00000000000");
            }

            // 🔍 Busca cliente existente ou cria um novo
            Cliente cliente = clienteRepository.findByCpf(cpfLimpo)
                    .orElseGet(() -> {
                        Cliente novo = new Cliente();
                        novo.setNome(request.getNomeCliente());
                        novo.setCpf(cpfLimpo);
                        return clienteRepository.save(novo);
                    });

            // 💾 Cria nova conta vinculada ao cliente
            Conta conta = new Conta();
            conta.setTipo(request.getTipoConta());
            conta.setStatus(StatusConta.ATIVA);
            conta.setCliente(cliente);

            contaRepository.save(conta);

            System.out.printf("✅ Conta criada para o cliente %s (%s)%n", cliente.getNome(), cliente.getCpf());

        } catch (Exception e) {
            System.err.printf("❌ Erro ao processar mensagem Kafka: %s%n", e.getMessage());
            // Se necessário, rethrow para permitir retry controlado:
            // throw e;
        }
    }
}