package com.funcoes.logging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Representa um registro de log processado pelo Log Service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEntry {

    private String level;
    private String service;
    private String message;
    private LocalDateTime timestamp;

}
