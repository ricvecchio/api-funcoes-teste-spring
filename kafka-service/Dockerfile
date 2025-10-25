# Etapa final - executa o JAR do serviço de Kafka
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY target/*.jar app.jar

# 🐶 Adiciona o Datadog Java Agent
RUN curl -L -o dd-java-agent.jar https://dtdg.co/latest-java-tracer

EXPOSE 8082

ENTRYPOINT ["java", "-javaagent:/app/dd-java-agent.jar", "-jar", "app.jar"]
