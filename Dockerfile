FROM eclipse-temurin:25-jre-jammy

LABEL authors="Kate Khreshkova"

WORKDIR /app

COPY build/libs/task-microservice-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080


ENTRYPOINT ["java", "-jar", "app.jar"]