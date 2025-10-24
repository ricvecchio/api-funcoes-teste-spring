package com.funcoes.logging;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * AutoConfiguração para ativar o Datadog e futuras integrações de logging.
 * Todos os microserviços que incluem o logging-lib herdam esta configuração.
 */
@AutoConfiguration
@Import({ DatadogConfiguration.class })
public class LoggingAutoConfiguration {
}
