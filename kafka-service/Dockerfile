# Build stage
FROM ubuntu:latest AS build

# Atualiza pacotes e instala JDK
RUN apt-get update && apt-get install -y openjdk-21-jdk maven

# Copia todo o projeto para o container
COPY . .

# Compila o projeto com Maven
RUN mvn clean install

# Runtime stage
FROM openjdk:21-jdk-slim

# Expondo porta da aplicação
EXPOSE 8080

# Copia o jar gerado da build
COPY --from=build /target/api-funcoes-teste-spring-0.0.1-SNAPSHOT.jar app.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]