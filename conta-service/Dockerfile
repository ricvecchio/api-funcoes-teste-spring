# ===== Build Stage =====
# Usa Ubuntu como base para instalar JDK 21 e Maven
FROM ubuntu:latest AS build

# Atualiza pacotes e instala JDK 21 e Maven
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven && \
    rm -rf /var/lib/apt/lists/*

# Define diretório de trabalho
WORKDIR /app

# Copia todo o projeto raiz (multi-módulo) para o container
COPY . .

# Compila todos os módulos (conta-service, kafka-service) e instala o jar
RUN mvn clean install -DskipTests

# ===== Runtime Stage =====
# Imagem enxuta com apenas JDK 21 para rodar o serviço
FROM openjdk:21-jdk-slim

# Define diretório de trabalho
WORKDIR /app

# Expondo porta padrão da aplicação
EXPOSE 8080

# Copia o jar específico do conta-service da build stage
COPY --from=build /app/conta-service/target/*.jar app.jar

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]