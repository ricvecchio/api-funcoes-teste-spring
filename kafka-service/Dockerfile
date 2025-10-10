# ===== Build Stage =====
FROM ubuntu:latest AS build

# Atualiza pacotes e instala JDK 21 + Maven
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven && \
    apt-get clean

WORKDIR /app

# Copia todo o projeto
COPY . .

# Build do projeto gerando o JAR, pulando testes
RUN mvn clean install -DskipTests

# ===== Runtime Stage =====
FROM openjdk:21-jdk-slim

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
