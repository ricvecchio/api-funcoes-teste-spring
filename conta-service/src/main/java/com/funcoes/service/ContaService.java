package com.funcoes.service;

import com.funcoes.model.AbrirContaRequest;
import com.funcoes.model.Cliente;
import com.funcoes.model.Conta;
import com.funcoes.model.StatusConta;
import com.funcoes.repository.ClienteRepository;
import com.funcoes.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContaService {

    private final ClienteRepository clienteRepository;
    private final ContaRepository contaRepository;
    @Autowired(required = false)
    private final KafkaTemplate<String, Conta> kafkaTemplate;

    @Transactional
    public void abrirConta(AbrirContaRequest request) {
        Cliente cliente = clienteRepository.findByCpf(request.cpf())
                .orElseGet(() -> clienteRepository.save(new Cliente(request.nomeCliente(), request.cpf())));

        Conta conta = new Conta();
        conta.setCliente(cliente);
        conta.setTipo(request.tipoConta());
        conta.setStatus(StatusConta.PENDENTE);

        contaRepository.save(conta);

        kafkaTemplate.send("conta-a-abrir", conta);
    }

    @Transactional(readOnly = true)
    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }

}
