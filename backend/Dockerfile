# Stage 1: Build the Spring Boot application using Maven and JDK 21
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Lightweight runtime image
FROM eclipse-temurin:21-jre-alpine
RUN adduser -D -u 1000 appuser
WORKDIR /app
COPY --from=build --chown=appuser:appuser /app/target/*.jar app.jar
COPY --chown=appuser:appuser uploads ./uploads

USER appuser

EXPOSE 7860
ENV PORT=7860
ENTRYPOINT ["java", "-jar", "app.jar"]
