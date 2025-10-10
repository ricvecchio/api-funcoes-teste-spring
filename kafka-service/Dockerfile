# ===== Build Stage =====
FROM ubuntu:latest AS build

RUN apt-get update && apt-get install -y openjdk-21-jdk maven
WORKDIR /app

# Copia toda a raiz do projeto para que o parent POM seja resolvido
COPY . .

# Build do módulo kafka-service, incluindo dependências
RUN mvn clean install -pl kafka-service -am -DskipTests

# ===== Runtime Stage =====
FROM openjdk:21-jdk-slim

WORKDIR /app
EXPOSE 8080

# Copia o JAR do módulo específico
COPY --from=build /app/kafka-service/target/kafka-service-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]