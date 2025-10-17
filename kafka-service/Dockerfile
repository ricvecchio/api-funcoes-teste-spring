FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Instalar curl e netcat para health checks
RUN apt-get update && apt-get install -y curl netcat && rm -rf /var/lib/apt/lists/*

# Copiar JAR da aplicação
COPY target/*.jar app.jar

# Porta interna do container definida via variável de ambiente
ARG SERVER_PORT=8080
ENV SERVER_PORT=${SERVER_PORT}
EXPOSE ${SERVER_PORT}

# Healthcheck usando a porta interna via variável de ambiente
HEALTHCHECK --interval=20s --timeout=10s --start-period=60s --retries=5 \
    CMD bash -c "curl -f http://localhost:${SERVER_PORT}/actuator/health || exit 1"

# Entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]