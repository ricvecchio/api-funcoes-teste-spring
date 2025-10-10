# ===== Build Stage =====
# Usa imagem Maven oficial com JDK 21 para compilar a aplicação
FROM maven:3.9.2-jdk-21 AS build

# Define diretório de trabalho
WORKDIR /app

# Copia o pom.xml e o código-fonte
COPY pom.xml .
COPY src ./src

# Compila o projeto com Maven (pulando testes)
RUN mvn clean package -DskipTests

# ===== Runtime Stage =====
# Usa imagem JDK 21 slim para rodar a aplicação
FROM eclipse-temurin:21-jdk-jammy

# Expondo porta da aplicação
EXPOSE 8080

# Copia o jar gerado da build
COPY --from=build /app/target/conta-service-1.0.0.jar app.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]