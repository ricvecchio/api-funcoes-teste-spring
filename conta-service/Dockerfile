# ======================================
# 🔹 Imagem base com JDK 21 (Eclipse Temurin)
# ======================================
FROM eclipse-temurin:21-jre-jammy

# ======================================
# 🔹 Diretório de trabalho dentro do container
# ======================================
WORKDIR /app

# ======================================
# 🔹 Instalação de utilitários necessários para healthcheck e debugging
# curl: usado pelo HEALTHCHECK
# ======================================
RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/*

# ======================================
# 🔹 Copia o JAR gerado pelo Maven para o container
# Usa pattern matching para pegar o JAR na pasta target
# ======================================
COPY target/*.jar app.jar

# ======================================
# 🔹 Exposição da porta interna da aplicação (Spring Boot)
# Deve corresponder ao server.port do application.properties
# ======================================
EXPOSE 8080

# ======================================
# 🔹 Healthcheck para Docker
# Verifica se o endpoint /actuator/health retorna sucesso
# Intervalo: 20s, Timeout: 10s, Start-period: 60s, Tentativas: 5
# ======================================
HEALTHCHECK --interval=20s --timeout=10s --start-period=60s --retries=5 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# ======================================
# 🔹 Comando de inicialização da aplicação
# ======================================
ENTRYPOINT ["java", "-jar", "app.jar"]
