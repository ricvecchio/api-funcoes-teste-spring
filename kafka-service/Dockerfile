# Build stage
FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /workspace

# Copiar projeto completo
COPY .. .

# Build do projeto raiz
RUN mvn -f /workspace/pom.xml clean install -DskipTests -q

# Build do microserviço
RUN mvn -f /workspace/kafka-service/pom.xml clean package -DskipTests -q

# Runtime stage
FROM eclipse-temurin:21-jre-slim

WORKDIR /app

# Copiar JAR
COPY --from=build /workspace/kafka-service/target/*.jar app.jar

# Expor porta
EXPOSE 8080

# Comando de execução com otimizações
ENTRYPOINT ["java", \
    "-Xmx512m", \
    "-Xms256m", \
    "-XX:+UseContainerSupport", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", \
    "app.jar"]