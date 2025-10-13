# ===== Build Stage =====
# Usar imagem oficial Maven com Java 21 (CORREÇÃO: Imagem oficial)
FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar arquivos de configuração do Maven primeiro (para cache de dependências)
COPY pom.xml .
COPY src ./src

# Compilar e gerar JAR (CORREÇÃO: Usar perfil correto)
RUN mvn clean package -DskipTests

# ===== Runtime Stage =====
# Imagem final leve apenas com JRE 21 (CORREÇÃO: Usar JRE slim)
FROM eclipse-temurin:21-jre-slim

WORKDIR /app

# Criar usuário não-root para segurança (BOA PRÁTICA)
RUN groupadd -r spring && useradd -r -g spring spring
USER spring

EXPOSE 8080

# Copiar JAR gerado (CORREÇÃO: Caminho correto)
COPY --from=build /app/target/*.jar app.jar

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]