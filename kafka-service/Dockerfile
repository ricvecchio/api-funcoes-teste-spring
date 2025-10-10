# ===== Build Stage =====
# Imagem base Ubuntu para instalar JDK 21 e Maven
FROM ubuntu:latest AS build

# Atualiza pacotes e instala JDK 21 e Maven
RUN apt-get update && apt-get install -y openjdk-21-jdk maven

# Define diretório de trabalho
WORKDIR /app

# Copia arquivos Maven e código-fonte
COPY pom.xml .
COPY src ./src

# Compila o projeto com Maven, pulando testes para agilizar build
RUN mvn clean install -DskipTests

# ===== Runtime Stage =====
# Imagem enxuta com OpenJDK 21
FROM openjdk:21-jdk-slim

# Define diretório de trabalho
WORKDIR /app

# Expondo porta da aplicação
EXPOSE 8080

# Copia o JAR gerado da build
COPY --from=build /app/target/*.jar app.jar

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]