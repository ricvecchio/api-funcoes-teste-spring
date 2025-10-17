# ======================================
# Imagem base: Java 21 JRE (Eclipse Temurin)
# ======================================
FROM eclipse-temurin:21-jre-jammy

# ======================================
# Diretório de trabalho dentro do container
# ======================================
WORKDIR /app

# ======================================
# Instalação de curl para healthchecks e netcat para dependências
# ======================================
RUN apt-get update && apt-get install -y curl netcat-openbsd \
    && rm -rf /var/lib/apt/lists/*

# ======================================
# Copiar o JAR construído pelo Maven para dentro do container
# ======================================
COPY target/*.jar app.jar

# ======================================
# Expor porta do container
# NOTA: Porta interna do container é 8080
# O mapeamento externo é feito via docker-compose usando a variável ${CONTA_SERVICE_PORT}
# ======================================
EXPOSE 8080

# ======================================
# Healthcheck para GitHub Actions / Docker
# ======================================
HEALTHCHECK --interval=20s --timeout=10s --start-period=60s --retries=5 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# ======================================
# Comando de entrada: espera dependências e inicia a aplicação
# ======================================
ENTRYPOINT ["sh", "-c", "\
  echo '🕒 Esperando Kafka e Postgres...' && \
  for i in {1..30}; do \
    nc -z kafka-broker ${KAFKA_BROKER_PORT} && nc -z postgres-db ${DB_PORT} && break; \
    echo '⏳ Aguardando dependências... ($i/30)'; sleep 3; \
  done && \
  echo '✅ Dependências disponíveis! Iniciando Conta Service...' && \
  java -jar /app/app.jar \
"]
