# ===== Build Stage =====
# Usa imagem Maven com JDK 21 existente para compilar a aplicação
FROM maven:3.9.2-eclipse-temurin-21 AS build

WORKDIR /app

# Copia pom.xml e código-fonte
COPY pom.xml .
COPY src ./src

# Compila o projeto com Maven (pulando testes)
RUN mvn clean package -DskipTests

# ===== Runtime Stage =====
FROM eclipse-temurin:21-jdk-jammy

EXPOSE 8080

COPY --from=build /app/target/kafka-service-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]