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

@Component
@RequiredArgsConstructor
public class ContaConsumer {

    private final ObjectMapper objectMapper;
    private final ClienteRepository clienteRepository;
    private final ContaRepository contaRepository;

    @KafkaListener(topics = "${conta.aberturas.topic}", groupId = "kafka-service")
    public void consume(String message) {
        try {
            AbrirContaRequest request = objectMapper.readValue(message, AbrirContaRequest.class);

            // 🧹 Normaliza o CPF
            String cpfLimpo = request.getCpf().replaceAll("\\D", "");

            // 🔍 Busca cliente existente ou cria novo
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
        }
    }
}
