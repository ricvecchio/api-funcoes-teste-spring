# Build stage
FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /workspace

# Copiar projeto completo para resolver dependências
COPY .. .

# Build do projeto raiz primeiro
RUN mvn -f /workspace/pom.xml clean install -DskipTests -q

# Build do microserviço
RUN mvn -f /workspace/conta-service/pom.xml clean package -DskipTests -q

# Runtime stage
FROM eclipse-temurin:21-jre-slim

WORKDIR /app

# Copiar JAR
COPY --from=build /workspace/conta-service/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]