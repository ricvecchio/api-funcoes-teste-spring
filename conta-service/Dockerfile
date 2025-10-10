# ===== Build Stage =====
# Ubuntu + JDK 21 + Maven
FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven && \
    apt-get clean

WORKDIR /app

# Copia todo o código do microserviço
COPY . .

# Compila e gera o JAR pulando testes
RUN mvn clean install -DskipTests

# ===== Runtime Stage =====
# Imagem final leve apenas com JDK 21
FROM openjdk:21-jdk-slim

WORKDIR /app

EXPOSE 8080

# Copia o JAR gerado
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
