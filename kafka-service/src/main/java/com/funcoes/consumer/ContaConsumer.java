package com.funcoes.consumer;

import com.funcoes.logging.LogClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ContaConsumer {

    @Autowired
    private LogClient logClient;

    @KafkaListener(topics = "contas", groupId = "funcoes-grupo")
    public void consumir(String mensagem) {
        try {
            logClient.info("KafkaService", "ContaConsumer", "Mensagem recebida: " + mensagem);
        } catch (Exception e) {
            logClient.error("KafkaService", "ContaConsumer", "Erro ao processar mensagem", e);
        }
    }
}

