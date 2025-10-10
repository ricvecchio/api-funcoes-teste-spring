# ===== Build Stage =====
FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven wget tar && \
    apt-get clean

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

# ===== Runtime Stage =====
FROM openjdk:21-jdk-slim

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
