FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Instalar curl para health checks
USER root
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# Copiar JAR da aplicação
COPY target/kafka-service-*.jar app.jar

# Expor porta
EXPOSE 8080

# Health check melhorado
HEALTHCHECK --interval=30s --timeout=10s --start-period=120s --retries=5 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Entrada da aplicação com melhor configuração de memória
ENTRYPOINT ["java", "-jar", \
            "-Dspring.profiles.active=docker", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "app.jar"]