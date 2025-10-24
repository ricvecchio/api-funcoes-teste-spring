package com.funcoes.log.config;

import com.funcoes.logging.LogClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração manual do LogClient para o Log Service.
 *
 * Esta configuração cria e registra o bean LogClient no contexto do Spring,
 * permitindo que ele seja injetado em controladores e serviços.
 */
@Configuration
public class LoggingConfig {

    @Bean
    public LogClient logClient() {
        System.out.println("✅ Bean LogClient registrado manualmente para o LogService");
        return new LogClient();
    }
}
