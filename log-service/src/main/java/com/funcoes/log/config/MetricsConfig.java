package com.funcoes.log.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class MetricsConfig {

    public MetricsConfig(MeterRegistry registry,
                         @Value("${spring.application.name:log-service}") String appName) {
        registry.config().commonTags("application", appName);
    }
}
