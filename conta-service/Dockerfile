# ===== Build Stage =====
# Usar imagem oficial Maven com Java 21
FROM maven:3.9.8-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copiar arquivos de configuração do Maven primeiro (para cache de dependências)
COPY pom.xml .
# Copiar código fonte
COPY src ./src

# Compilar e gerar JAR
RUN mvn clean package -DskipTests

# ===== Runtime Stage =====
# CORREÇÃO: Usar imagem existente do Eclipse Temurin
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Criar usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

EXPOSE 8080

# Copiar JAR gerado
COPY --from=build /app/target/*.jar app.jar

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]