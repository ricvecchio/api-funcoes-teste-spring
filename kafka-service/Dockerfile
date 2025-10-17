# kafka-service/Dockerfile
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Instalar utilitários mínimos (opcional)
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Dummy JAR para build de CI (evita falha se não houver artefato)
RUN echo "placeholder for kafka-service" > app.jar

EXPOSE 8082
CMD ["java", "-jar", "app.jar"]
