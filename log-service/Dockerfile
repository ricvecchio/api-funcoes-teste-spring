# Etapa final - executa o JAR do serviço de logs
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copia o artefato gerado pelo Maven
COPY target/*.jar app.jar

# Porta interna do container
EXPOSE 8083

# Executa o serviço
ENTRYPOINT ["java", "-jar", "app.jar"]