# ===== Build Stage =====
FROM ubuntu:latest AS build

# Instala JDK e Maven
RUN apt-get update && apt-get install -y openjdk-21-jdk maven

# Define diretório de trabalho
WORKDIR /app

# Copia todos os módulos para resolver o parent POM
COPY ../api-funcoes-teste-spring ./api-funcoes-teste-spring
COPY . .

# Compila o módulo conta-service e suas dependências
RUN mvn clean install -pl conta-service -am -DskipTests

# ===== Runtime Stage =====
FROM openjdk:21-jdk-slim

# Porta da aplicação
EXPOSE 8080

# Copia o jar compilado
COPY --from=build /app/conta-service/target/conta-service-1.0.0.jar app.jar

# Comando para rodar
ENTRYPOINT ["java", "-jar", "app.jar"]