package com.funcoes.logging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa uma entrada de log que será enviada para o Log Service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEntry {

    private String origem;
    private String acao;
    private String nivel;
    private String mensagem;
}
