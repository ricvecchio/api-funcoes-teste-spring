package com.funcoes.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementação genérica de logger para integração com Splunk ou outros destinos.
 * Essa classe padroniza logs e pode ser usada por qualquer serviço.
 *
 * Estrutura padrão:
 * [nível] [serviço] [ação] -> mensagem
 *
 * Exemplo:
 * INFO  [kafka-service] [consumirMensagem] -> Mensagem recebida com sucesso.
 */
@Slf4j
@Component
public class SplunkLogger {

    /**
     * Loga uma mensagem informativa.
     */
    public void info(String serviceName, String action, String message) {
        log.info(format(serviceName, action, message));
    }

    /**
     * Loga uma mensagem de sucesso.
     */
    public void success(String serviceName, String action, String resource, String message) {
        log.info(format(serviceName, action, String.format("SUCESSO: %s | Recurso: %s", message, resource)));
    }

    /**
     * Loga um erro sem exceção.
     */
    public void error(String serviceName, String action, String message) {
        log.error(format(serviceName, action, message));
    }

    /**
     * Loga um erro com exceção (stack trace incluído).
     */
    public void error(String serviceName, String action, String message, Exception e) {
        log.error(format(serviceName, action, message), e);
    }

    /**
     * Monta a string padronizada de log.
     */
    private String format(String serviceName, String action, String message) {
        return String.format("[%s] [%s] -> %s", serviceName, action, message);
    }
}
