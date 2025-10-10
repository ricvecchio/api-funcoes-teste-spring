package com.funcoes.service;

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

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaContaService {

    private final ContaRepository contaRepository;

    public void processarConta(Conta conta) {
        Conta contaBanco = contaRepository.findById(conta.getIdConta())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada: " + conta.getIdConta()));


        // Simula verificação externa: 50% de chance de o sistema estar disponível
        boolean sucesso = Math.random() > 0.5;
        contaBanco.setStatus(sucesso ? StatusConta.ABERTA : StatusConta.FALHA);
        contaRepository.save(contaBanco);

        log.info("Conta {} processada com status {}", contaBanco.getIdConta(), contaBanco.getStatus());
    }
}

