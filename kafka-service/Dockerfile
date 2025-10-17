FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Instalar curl para health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copiar JAR da aplicação
COPY target/*.jar app.jar

# Expor porta definida via variável (internamente no container, deve coincidir com server.port)
EXPOSE ${KAFKA_SERVICE_PORT}

# Healthcheck ajustado para usar a porta interna do container (default 8080)
HEALTHCHECK --interval=20s --timeout=10s --start-period=60s --retries=5 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]
