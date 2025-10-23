package com.funcoes.consumer;

import com.funcoes.util.KafkaLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumer Kafka responsável por receber e processar mensagens do tópico "conta-topic".
 */
@Component
@RequiredArgsConstructor
public class ContaConsumer {

    private final KafkaLogger kafkaLogger;

    /**
     * Método executado automaticamente quando chega mensagem no tópico Kafka.
     */
    @KafkaListener(topics = "conta-topic", groupId = "grupo-conta")
    public void consumirMensagem(String mensagem) {
        String action = "consumirMensagem";
        try {
            // Loga o recebimento da mensagem
            kafkaLogger.info(action, "Mensagem recebida: " + mensagem);

            // Processa a mensagem
            processarMensagem(mensagem);

            // Loga sucesso
            kafkaLogger.success(action, mensagem, "Mensagem processada com sucesso.");
        } catch (Exception e) {
            // Loga erro com exceção
            kafkaLogger.error(action, "Erro ao processar mensagem: " + mensagem, e);
        }
    }

    /**
     * Simulação de processamento da mensagem Kafka.
     * Aqui pode ir qualquer lógica de negócio.
     */
    private void processarMensagem(String mensagem) {
        if (mensagem == null || mensagem.isBlank()) {
            throw new IllegalArgumentException("Mensagem vazia recebida no tópico Kafka.");
        }

        // Exemplo de lógica: parsing, persistência, integração, etc.
        System.out.println("Processando mensagem: " + mensagem);
    }
}
