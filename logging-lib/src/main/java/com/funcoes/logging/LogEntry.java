package com.funcoes.logging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa uma entrada de log padronizada compartilhada entre os serviços.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEntry {
    private String timestamp;  // Data/hora do evento
    private String origem;     // Serviço ou componente de origem
    private String acao;       // Ação executada
    private String nivel;      // INFO, WARN, ERROR, etc.
    private String mensagem;   // Descrição detalhada
}
