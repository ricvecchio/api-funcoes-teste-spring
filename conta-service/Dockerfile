# ===== Build Stage =====
# Usa Ubuntu como base para instalar JDK e Maven
FROM ubuntu:latest AS build

# Atualiza pacotes, instala JDK 21 e Maven
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven && \
    rm -rf /var/lib/apt/lists/*

# Define diretório de trabalho
WORKDIR /app

# Copia todo o projeto multi-módulo para o container
COPY ../ ./

# Compila somente o módulo conta-service e suas dependências
RUN mvn clean install -pl conta-service -am -DskipTests

# ===== Runtime Stage =====
FROM openjdk:21-jdk-slim

WORKDIR /app

# Expõe a porta do serviço
EXPOSE 8080

# Copia o jar gerado do build stage
COPY --from=build /app/conta-service/target/*.jar app.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]