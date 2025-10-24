package com.funcoes.logging;

import io.micrometer.core.instrument.Clock;
import io.micrometer.datadog.DatadogConfig;
import io.micrometer.datadog.DatadogMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Optional;

/**
 * Configuração global do Datadog para integração com Micrometer.
 *
 * Essa classe define o DatadogConfig e o DatadogMeterRegistry.
 * Evita conflito de nomes com a interface oficial DatadogConfig do Micrometer.
 */
@Configuration
public class DatadogConfiguration {

    @Bean
    public DatadogConfig datadogConfig() {
        return new DatadogConfig() {

            @Override
            public String get(String key) {
                return null;
            }

            @Override
            public String apiKey() {
                // Busca variável de ambiente ou System property com fallback
                return Optional.ofNullable(System.getenv("MANAGEMENT_DATADOG_METRICS_EXPORT_APIKEY"))
                        .orElseGet(() -> System.getProperty(
                                "management.datadog.metrics.export.apiKey",
                                "dummy-key"));
            }

            @Override
            public String uri() {
                return "https://api.datadoghq.com";
            }

            @Override
            public boolean enabled() {
                return true;
            }

            @Override
            public Duration step() {
                return Duration.ofSeconds(30);
            }
        };
    }

    @Bean
    public DatadogMeterRegistry datadogMeterRegistry(DatadogConfig datadogConfig) {
        return new DatadogMeterRegistry(datadogConfig, Clock.SYSTEM);
    }
}
