package com.funcoes.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Cliente para envio padronizado de logs entre os serviços.
 */
@Component
public class LogClient {

    private static final Logger logger = LoggerFactory.getLogger(LogClient.class);

    private LogEntry buildLog(String origem, String acao, String nivel, String mensagem) {
        return LogEntry.builder()
                .timestamp(Instant.now().toString())
                .origem(origem)
                .acao(acao)
                .nivel(nivel)
                .mensagem(mensagem)
                .build();
    }

    public void info(String origem, String acao, String mensagem) {
        LogEntry entry = buildLog(origem, acao, "INFO", mensagem);
        logger.info(entry.toString());
    }

    public void warn(String origem, String acao, String mensagem) {
        LogEntry entry = buildLog(origem, acao, "WARN", mensagem);
        logger.warn(entry.toString());
    }

    public void error(String origem, String acao, String mensagem, Exception e) {
        LogEntry entry = buildLog(origem, acao, "ERROR", mensagem);
        logger.error(entry.toString(), e);
    }

    public void error(String origem, String acao, String mensagem) {
        error(origem, acao, mensagem, null);
    }

    public void consume(String origem, String acao, String mensagem) {
        info(origem, acao, mensagem);
    }

    public void success(String origem, String acao, String mensagem, String detalhes) {
        info(origem, acao, mensagem + " | Detalhes: " + detalhes);
    }

}
