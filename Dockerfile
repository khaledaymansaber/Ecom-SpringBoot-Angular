# Stage 1: Build the Spring Boot application using Maven and JDK 21
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY backend/pom.xml .
COPY backend/src ./src
RUN mvn clean package -DskipTests

# Stage 2: Lightweight runtime image with non-root user (UID 1000 for Hugging Face Spaces compatibility)
FROM eclipse-temurin:21-jre-alpine
RUN adduser -D -u 1000 appuser
WORKDIR /app

COPY --from=build --chown=appuser:appuser /app/target/*.jar app.jar
COPY --chown=appuser:appuser backend/uploads ./uploads

USER appuser

EXPOSE 7860
ENV PORT=7860

ENTRYPOINT ["java", "-jar", "app.jar"]
