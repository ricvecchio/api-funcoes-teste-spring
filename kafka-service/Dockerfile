FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Instalar curl para health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copiar JAR gerado pelo Maven
COPY target/*.jar app.jar

# Expor porta do serviço
EXPOSE ${KAFKA_SERVICE_PORT}

# Healthcheck usando porta variável
HEALTHCHECK --interval=20s --timeout=10s --start-period=60s --retries=5 \
    CMD curl -f http://localhost:${KAFKA_SERVICE_PORT}/actuator/health || exit 1

# Comando de inicialização do Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
