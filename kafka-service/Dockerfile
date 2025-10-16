# ======================================
# 🔹 Imagem base com JDK 21
# ======================================
FROM eclipse-temurin:21-jre-jammy

# ======================================
# 🔹 Diretório de trabalho
# ======================================
WORKDIR /app

# ======================================
# 🔹 Instala utilitários necessários
# curl: usado para healthcheck
# ======================================
RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/*

# ======================================
# 🔹 Copia JAR gerado pelo Maven
# ======================================
COPY target/*.jar app.jar

# ======================================
# 🔹 Porta interna do container
# ======================================
EXPOSE 8080

# ======================================
# 🔹 Healthcheck usando endpoint do Spring Boot
# ======================================
HEALTHCHECK --interval=20s --timeout=10s --start-period=60s --retries=5 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# ======================================
# 🔹 Comando de inicialização
# ======================================
ENTRYPOINT ["java", "-jar", "app.jar"]