package com.funcoes.log.config;

import io.micrometer.datadog.DatadogConfig;
import io.micrometer.datadog.DatadogMeterRegistry;
import io.micrometer.core.instrument.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatadogConfigImpl {

    @Bean
    public DatadogConfig datadogConfig() {
        return new DatadogConfig() {
            @Override
            public String apiKey() {
                // pega da env (Docker ou .env.local)
                return System.getenv("DATADOG_API_KEY");
            }

            @Override
            public String get(String key) {
                return null;
            }
        };
    }

    @Bean
    public DatadogMeterRegistry datadogMeterRegistry(DatadogConfig config) {
        return DatadogMeterRegistry.builder(config).clock(Clock.SYSTEM).build();
    }
}
