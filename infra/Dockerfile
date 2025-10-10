# ===== Build Stage =====
# Usamos Ubuntu como base para instalar JDK 21 e Maven
FROM ubuntu:latest AS build

# Define argumento para escolher o módulo a ser buildado
# Exemplo: --build-arg MODULE=conta-service
ARG MODULE

# Define diretório de trabalho
WORKDIR /app

# Atualiza pacotes e instala JDK 21 e Maven
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven && \
    apt-get clean

# Copia todos os módulos do projeto para resolver o parent POM
COPY ./conta-service ./conta-service
COPY ./kafka-service ./kafka-service
COPY ./generico-service ./generico-service
COPY ./pom.xml ./

# Compila somente o módulo selecionado com suas dependências, pulando testes
# Isso resolve o parent POM sem precisar instalar nada no Maven Central
RUN mvn clean install -pl $MODULE -am -DskipTests

# ===== Runtime Stage =====
# Base menor com OpenJDK 21 para rodar a aplicação
FROM openjdk:21-jdk-slim

# Define diretório de trabalho
WORKDIR /app

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Copia o jar compilado do build stage
# Ajuste o nome do jar conforme seu módulo e versão
COPY --from=build /app/$MODULE/target/*.jar app.jar

# Entry point da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]