# ===== Build Stage =====
# Usa Ubuntu para build do projeto com JDK 21 e Maven
FROM ubuntu:latest AS build

# Atualiza pacotes e instala JDK e Maven
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven && \
    rm -rf /var/lib/apt/lists/*

# Define diretório de trabalho
WORKDIR /app

# Copia todo o projeto multi-módulo
COPY ../ ./

# Compila todo o projeto para resolver o parent POM
RUN mvn clean install -DskipTests

# ===== Runtime Stage =====
# Usa imagem leve do OpenJDK 21
FROM openjdk:21-jdk-slim

WORKDIR /app

# Expondo porta da aplicação
EXPOSE 8080

# Copia apenas o JAR do serviço específico
COPY --from=build /app/kafka-service/target/*.jar app.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]