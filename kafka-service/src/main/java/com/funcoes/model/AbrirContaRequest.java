package com.funcoes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO usado exclusivamente no kafka-service para representar a mensagem recebida do tópico Kafka.
 * <p>
 * Esse objeto é a estrutura do JSON publicado pelo conta-service, contendo:
 * - nomeCliente
 * - cpf
 * - tipoConta
 * <p>
 * Observações:
 * - Não contém validações complexas nem anotações JPA.
 * - O CPF é recebido "como veio" e normalizado no consumer.
 * - É deliberadamente simples para manter o baixo acoplamento entre microserviços.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AbrirContaRequest {

    private String nomeCliente;
    private String cpf;
    private String tipoConta;

    // Nenhum comportamento adicional: o consumidor trata a limpeza e validação.
}
