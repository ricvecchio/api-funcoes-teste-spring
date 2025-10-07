package com.funcoes.component;

import com.funcoes.model.Conta;
import com.funcoes.service.ContaService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ContaConsumer {
    private final ContaService contaService;

    @KafkaListener(topics = "conta-a-abrir", groupId = "grupo-conta")
    @RetryableTopic(attempts = "3", backoff = @Backoff(delay = 2000), autoCreateTopics = "false")
    public void processarContaKafka(Conta conta) {
        log.info("Consumindo conta {} do cliente {}", conta.getIdConta(), conta.getCliente().getNome());
        contaService.processarContaPendente(conta);
    }

    @DltHandler
    public void tratarFalha(Conta conta) {
        log.error("Conta {} enviada para Dead Letter Topic após falhas. Erro: {}", conta.getIdConta());
    }

}



