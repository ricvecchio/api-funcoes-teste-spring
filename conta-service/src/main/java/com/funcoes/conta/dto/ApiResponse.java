package com.funcoes.conta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String mensagem;
    private String status;
    private Instant timestamp;
}
