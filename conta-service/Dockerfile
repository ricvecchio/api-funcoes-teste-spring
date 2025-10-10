# ===== Build Stage =====
# Usa Ubuntu para instalar JDK 21 e Maven
FROM ubuntu:latest AS build

# Instala JDK 21 e Maven
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven && \
    rm -rf /var/lib/apt/lists/*

# Define diretório de trabalho
WORKDIR /app

# Copia todo o projeto raiz (multi-módulo)
COPY . .

# Compila todos os módulos e gera jars, pulando testes
RUN mvn clean install -DskipTests

# ===== Runtime Stage =====
# Imagem enxuta com apenas JDK 21 para rodar o serviço
FROM openjdk:21-jdk-slim

WORKDIR /app

# Expõe porta do serviço
EXPOSE 8080

# Copia o jar específico do conta-service
COPY --from=build /app/conta-service/target/*.jar app.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]