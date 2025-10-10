# ===== Build Stage =====
FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copia todo o projeto raiz (multi-módulo)
COPY . .

# Compila todos os módulos e gera jars, pulando testes
RUN mvn clean install -DskipTests

# ===== Runtime Stage =====
FROM openjdk:21-jdk-slim

WORKDIR /app

EXPOSE 8080

# Copia o jar específico do kafka-service
COPY --from=build /app/kafka-service/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]