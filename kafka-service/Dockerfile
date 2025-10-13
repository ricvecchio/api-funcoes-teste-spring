# ===== Build Stage =====
# Usar imagem oficial Maven com Java 21 para build
FROM maven:3.9.8-eclipse-temurin-21-alpine AS build

WORKDIR /workspace

# Copiar projeto completo para resolver dependências do parent POM
COPY .. .

# Build do projeto raiz primeiro para instalar o parent POM no repositório local
RUN mvn -f /workspace/pom.xml clean install -DskipTests

# Agora build do microserviço específico
RUN mvn -f /workspace/kafka-service/pom.xml clean package -DskipTests

# ===== Runtime Stage =====
# Imagem final leve com JRE 21 Alpine
FROM eclipse-temurin:21-jre-alpine

# Metadados da imagem
LABEL maintainer="sua-equipe@empresa.com"
LABEL description="Microserviço de Processamento Kafka"
LABEL version="1.0.0"

WORKDIR /app

# Criar usuário não-root para segurança (Boa Prática)
RUN addgroup -S spring && adduser -S spring -G spring

# Mudar para usuário não-root
USER spring

# Expor porta da aplicação
EXPOSE 8080

# Health check (Boa Prática para Orchestration)
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Copiar JAR gerado do stage de build
COPY --from=build --chown=spring:spring /workspace/kafka-service/target/*.jar app.jar

# Comando de execução com otimizações JVM para containers
ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-jar", \
    "app.jar"]