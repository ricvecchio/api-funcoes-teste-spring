FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Criar usuário não-root para segurança
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# Copiar JAR da aplicação
COPY target/conta-service-*.jar app.jar

# Expor porta
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/api/actuator/health || exit 1

# Entrada da aplicação com profile docker
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "app.jar"]