# ===== Build Stage =====
# Usamos Maven + JDK 21 para compilar o microserviço
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Diretório de trabalho no container
WORKDIR /app

# Copia pom.xml e código-fonte
COPY pom.xml .
COPY src ./src

# Compila o serviço, gerando o JAR no target (skip testes para CI/CD rápido)
RUN mvn clean package -DskipTests

# ===== Runtime Stage =====
# Imagem final leve apenas com JDK para rodar o JAR
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia o JAR compilado do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta do serviço
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
