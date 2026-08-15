# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Build Spring Boot jar, skip tests for faster deployment
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/car-rental-app-1.0-SNAPSHOT.jar app.jar

# Render uses the PORT environment variable
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
