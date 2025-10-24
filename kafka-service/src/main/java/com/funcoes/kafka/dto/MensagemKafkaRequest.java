package com.funcoes.kafka.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record MensagemKafkaRequest(
        Long idCliente,
        String nomeCliente,
        String tipoConta,
        BigDecimal saldoInicial,
        Instant dataAbertura
) {}

