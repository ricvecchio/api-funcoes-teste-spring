# ===== Build Stage =====
FROM maven:3.9.8-eclipse-temurin-21-alpine AS build

WORKDIR /app

# CORREÇÃO: Copiar projeto completo
COPY .. .

# CORREÇÃO: Build do projeto raiz primeiro
RUN mvn -f pom.xml clean install -DskipTests

# CORREÇÃO: Build do microserviço específico
RUN mvn -f kafka-service/pom.xml clean package -DskipTests

# ===== Runtime Stage =====
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Criar usuário não-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

EXPOSE 8080

# CORREÇÃO: Copiar JAR do microserviço específico
COPY --from=build /app/kafka-service/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]