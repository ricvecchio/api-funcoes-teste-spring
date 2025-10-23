package com.funcoes.logging;

import lombok.Builder;
import lombok.Data;

/**
 * Representa uma entrada de log estruturada.
 */
@Data
@Builder
public class LogEntry {
    private String serviceName;    // Nome do serviço (ex: conta-service, kafka-service)
    private String topicName;      // Nome do tópico (se aplicável)
    private String action;         // Ação (ex: CREATE, UPDATE, DELETE, etc.)
    private String level;          // Nível de log (INFO, WARN, ERROR, SUCCESS)
    private String message;        // Mensagem principal do log
    private String timestamp;      // 🕒 Timestamp do evento (gerado pelo LogClient)
}
