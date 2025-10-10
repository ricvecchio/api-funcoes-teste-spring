# ===== Build Stage =====
FROM ubuntu:latest AS build

# Definir variável de argumento para escolher o módulo a buildar
ARG MODULE
ENV MODULE=${MODULE}

# Atualiza e instala Java 21 + Maven
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven && \
    rm -rf /var/lib/apt/lists/*

# Define diretório de trabalho
WORKDIR /app

# Copia todos os módulos necessários para resolver o parent POM
COPY ./conta-service ./conta-service
COPY ./kafka-service ./kafka-service
COPY ./generico-service ./generico-service
COPY ./pom.xml ./pom.xml

# Compila apenas o módulo desejado e suas dependências
RUN mvn clean install -pl $MODULE -am -DskipTests

# ===== Runtime Stage =====
FROM openjdk:21-jdk-slim

ARG MODULE
ENV MODULE=${MODULE}

WORKDIR /app

# Expor porta 8080 (padrão dos microserviços)
EXPOSE 8080

# Copia o jar do build stage
COPY --from=build /app/$MODULE/target/$MODULE-0.0.1-SNAPSHOT.jar app.jar

# Define o entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]