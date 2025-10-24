# Etapa final - executa o JAR do serviço de contas
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copia o artefato gerado pelo Maven
COPY target/*.jar app.jar

# 🐶 Adiciona o Datadog Java Agent (auto-instrumentação)
RUN curl -L -o dd-java-agent.jar https://dtdg.co/latest-java-tracer

# Porta interna do container
EXPOSE 8081

# 🧩 Executa com o agente Datadog habilitado
ENTRYPOINT ["java", "-javaagent:/app/dd-java-agent.jar", "-jar", "app.jar"]
