# ===== Build Stage =====
# Imagem base Ubuntu para instalar JDK 21 e Maven
FROM ubuntu:latest AS build

# Atualiza pacotes e instala JDK 21 + Maven
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven && \
    apt-get clean

# Define diretório de trabalho dentro do container
WORKDIR /app

# Copia todo o projeto para o container
COPY . .

# Build do projeto gerando o JAR, pulando testes
RUN mvn clean install -DskipTests

# ===== Runtime Stage =====
# Imagem leve apenas com JDK 21 para rodar a aplicação
FROM openjdk:21-jdk-slim

WORKDIR /app

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Copia o JAR compilado do build stage
COPY --from=build /app/target/*.jar app.jar

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
