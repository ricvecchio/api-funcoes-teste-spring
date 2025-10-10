# ===== Build Stage =====
# Usamos Ubuntu para instalar JDK 21 e Maven
FROM ubuntu:latest AS build

# Atualiza pacotes e instala JDK 21 + Maven
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven && \
    apt-get clean

# Define diretório de trabalho dentro do container
WORKDIR /app

# Copia todo o projeto
COPY . .

# Compila o projeto, gerando o JAR no target, pulando testes
RUN mvn clean install -DskipTests

# ===== Runtime Stage =====
# Imagem final leve apenas com JDK 21 para rodar a aplicação
FROM openjdk:21-jdk-slim

WORKDIR /app

# Expõe a porta do serviço
EXPOSE 8080

# Copia o JAR compilado do build stage
COPY --from=build /app/target/*.jar app.jar

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
