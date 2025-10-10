# ===== Build Stage =====
FROM maven:3.9.2-eclipse-temurin-21 AS build

# Define diretório de trabalho
WORKDIR /app

# Copia apenas os arquivos de dependência primeiro para cache Maven
COPY pom.xml ./
COPY **/pom.xml ./

# Baixa dependências do Maven sem buildar ainda
RUN mvn dependency:go-offline -B

# Argumento para escolher qual módulo buildar
ARG MODULE

# Copia o código completo
COPY . .

# Compila o módulo escolhido e suas dependências, pulando testes
RUN mvn clean install -pl $MODULE -am -DskipTests

# ===== Runtime Stage =====
FROM openjdk:21-jdk-slim

# Argumento para definir módulo
ARG MODULE

WORKDIR /app

# Expõe a porta padrão (ajuste por serviço)
EXPOSE 8080

# Copia o JAR buildado do estágio anterior
COPY --from=build /app/$MODULE/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]