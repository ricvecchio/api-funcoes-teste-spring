package com.funcoes.log.dto;

import lombok.Data;

@Data
public class LogRequest {
    private String acao;
    private String mensagem;
}
