FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Instalar curl para health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copiar JAR da aplicação - usar pattern matching para encontrar o JAR
COPY target/*.jar app.jar

# Expor porta
EXPOSE 8080

# Health check
HEALTHCHECK --interval=20s --timeout=10s --start-period=60s --retries=5 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Entrada da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]