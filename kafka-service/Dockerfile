FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Instalar curl para health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copiar JAR da aplicação
COPY target/*.jar app.jar

# Expor porta padrão do Spring Boot (interna do container)
EXPOSE 8080

# Healthcheck usando endpoint de liveness para evitar marcar unhealthy por dependências externas.
HEALTHCHECK --interval=20s --timeout=10s --start-period=60s --retries=5 \
    CMD curl -f http://localhost:8080/actuator/health/liveness || exit 1

# Entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]