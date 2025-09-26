package com.funcoes.component;

import com.funcoes.model.Conta;
import com.funcoes.service.ContaService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ContaConsumer {

    private final ContaService contaService;
    private static final int MAX_RETRIES = 3;

    public ContaConsumer(ContaService contaService) {
        this.contaService = contaService;
    }

    @KafkaListener(topics = "conta-a-abrir", groupId = "grupo-conta")
    public void consumirConta(Conta conta) {
        int tentativa = 0;
        boolean sucesso = false;

        while (tentativa < MAX_RETRIES && !sucesso) {
            try {
                contaService.processarContaPendente(conta);
                sucesso = true;
            } catch (Exception e) {
                tentativa++;
                System.out.println("Tentativa " + tentativa + " falhou: " + e.getMessage());
                if (tentativa >= MAX_RETRIES) {
                    System.out.println("Número máximo de tentativas atingido. Conta ficará com status FALHA.");
                } else {
                    try {
                        Thread.sleep(2000); // delay de 2 segundos entre tentativas
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}


