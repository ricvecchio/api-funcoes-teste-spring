# Dockerfile do conta-service
FROM eclipse-temurin:21-jre-jammy

# Criar diretório da aplicação
WORKDIR /app

# Copiar o jar gerado pelo Maven
# Atenção: o jar deve ser gerado antes de rodar docker build
COPY target/conta-service-*.jar app.jar

# Porta da aplicação
EXPOSE 8081

# Comando para rodar o serviço
ENTRYPOINT ["java","-jar","app.jar"]
