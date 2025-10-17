FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copia o JAR já compilado pelo workflow
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
