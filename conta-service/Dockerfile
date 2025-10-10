# ===== Build Stage =====
FROM maven:3.9.2-eclipse-temurin-21 AS build

# Define diretório de trabalho
WORKDIR /app

# Copia apenas os arquivos do módulo conta-service e o pom.xml local
COPY pom.xml .
COPY src ./src

# Compila o projeto e gera o JAR
RUN mvn clean package -DskipTests

# ===== Runtime Stage =====
FROM eclipse-temurin:21-jdk-jammy

# Porta da aplicação
EXPOSE 8080

# Copia o JAR gerado para o runtime
COPY --from=build /app/target/conta-service-1.0.0.jar app.jar

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
