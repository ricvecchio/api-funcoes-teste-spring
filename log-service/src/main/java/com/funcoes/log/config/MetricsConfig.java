package com.funcoes.log.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração de métricas (Micrometer + Datadog, se disponível).
 * Esta classe é resiliente: o serviço sobe mesmo que o Datadog não esteja configurado.
 */
@Configuration
public class MetricsConfig {

    public MetricsConfig(MeterRegistry meterRegistry) {
        if (meterRegistry != null) {
            System.out.println("✅ Micrometer ativo: registrando métricas com " + meterRegistry.getClass().getSimpleName());
        } else {
            System.out.println("⚠️ Nenhum MeterRegistry disponível. Métricas desativadas (modo local).");
        }
    }

    // Construtor alternativo para fallback quando o bean não existir
    public MetricsConfig() {
        System.out.println("⚠️ Nenhum MeterRegistry detectado. Executando sem métricas.");
    }
}
