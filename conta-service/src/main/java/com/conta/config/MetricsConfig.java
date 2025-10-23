package com.conta.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * ✅ Adiciona tags comuns nas métricas enviadas ao Datadog.
 */
@Configuration
public class MetricsConfig {

    public MetricsConfig(MeterRegistry registry,
                         @Value("${spring.application.name:conta-service}") String appName) {
        registry.config().commonTags("application", appName);
    }
}
