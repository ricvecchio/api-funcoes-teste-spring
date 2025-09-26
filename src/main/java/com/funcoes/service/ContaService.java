package com.funcoes.service;

import com.funcoes.model.Cliente;
import com.funcoes.model.Conta;
import com.funcoes.model.StatusConta;
import com.funcoes.repository.ClienteRepository;
import com.funcoes.repository.ContaRepository;
import jakarta.transaction.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ContaService {

    private final ClienteRepository clienteRepository;
    private final ContaRepository contaRepository;
    private final KafkaTemplate<String, Conta> kafkaTemplate;

    public ContaService(ClienteRepository clienteRepository,
                        ContaRepository contaRepository,
                        KafkaTemplate<String, Conta> kafkaTemplate) {
        this.clienteRepository = clienteRepository;
        this.contaRepository = contaRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void abrirConta(Long idCliente, String nomeCliente, String tipoConta) {
        Cliente cliente = null;

        if (idCliente != null) {
            cliente = clienteRepository.findById(idCliente).orElse(null);
        }

        if (cliente == null) {
            cliente = new Cliente();
            cliente.setNome(nomeCliente);
            cliente = clienteRepository.save(cliente);
        }

        Conta conta = new Conta();
        conta.setTipo(tipoConta);
        conta.setCliente(cliente);

        if (verificarSistemaExterno()) {
            conta.setStatus(StatusConta.ABERTA);
            contaRepository.save(conta);
            System.out.println("Conta aberta com sucesso!");
        } else {
            conta.setStatus(StatusConta.PENDENTE);
            contaRepository.save(conta);
            kafkaTemplate.send("conta-a-abrir", conta);
            System.out.println("Sistema indisponível. Conta enviada para Kafka com status PENDENTE.");
        }
    }

    // Simula sistema externo
    public boolean verificarSistemaExterno() {
        return Math.random() > 0.5; // 50% chance de indisponível
    }

    @Transactional
    public void processarContaPendente(Conta conta) {
        // Recarrega conta do banco para garantir status atualizado
        Conta contaBanco = contaRepository.findById(conta.getIdConta()).orElseThrow();
        if (contaBanco.getStatus() != StatusConta.PENDENTE) {
            System.out.println("Conta já processada, ignorando.");
            return;
        }

        if (verificarSistemaExterno()) {
            contaBanco.setStatus(StatusConta.ABERTA);
            contaRepository.save(contaBanco);
            System.out.println("Conta processada com sucesso pelo consumidor Kafka!");
        } else {
            contaBanco.setStatus(StatusConta.FALHA); // marca como falha após tentativa
            contaRepository.save(contaBanco);
            System.out.println("Conta falhou ao processar no Kafka. Status definido como FALHA.");
        }
    }
}