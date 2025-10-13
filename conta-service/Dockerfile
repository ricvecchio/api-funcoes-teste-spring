# ===== Build Stage =====
FROM maven:3.9.8-eclipse-temurin-21-alpine AS build

WORKDIR /app

# CORREÇÃO: Copiar apenas os arquivos necessários do microserviço
COPY pom.xml .
COPY src ./src

# CORREÇÃO: Definir o projeto como independente (remover parent se necessário)
# Para build rápido, podemos copiar apenas o parent POM se necessário
RUN mvn clean package -DskipTests

# ===== Runtime Stage =====
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring

EXPOSE 8080

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]