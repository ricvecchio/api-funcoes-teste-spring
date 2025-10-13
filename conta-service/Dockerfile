# ===== Build Stage =====
FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /workspace

# Copiar projeto completo para resolver dependências
COPY .. .

# Build do projeto raiz primeiro
RUN mvn -f /workspace/pom.xml clean install -DskipTests -q

# Build do microserviço
RUN mvn -f /workspace/conta-service/pom.xml clean package -DskipTests -q

# ===== Runtime Stage =====
FROM eclipse-temurin:21-jre

# Metadados
LABEL maintainer="dev-team@company.com"
LABEL version="1.0.0"
LABEL description="Conta Service Microservice"

WORKDIR /app

# Criar usuário não-root
RUN groupadd -r spring && useradd -r -g spring spring

# Mudar para usuário não-root
USER spring

# Expor porta
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Copiar JAR
COPY --from=build --chown=spring:spring /workspace/conta-service/target/*.jar app.jar

# Variáveis de ambiente para otimização
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom -Dfile.encoding=UTF-8"
ENV SPRING_PROFILES_ACTIVE="docker"

# Comando de execução
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]