# Stage 1: Build the application using Maven and JDK 25
FROM maven:3.9.9-eclipse-temurin-25-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:25-jre-alpine
COPY --from=build /target/*.jar app.jar
# Use the PORT environment variable provided by Render
EXPOSE 8081
ENTRYPOINT ["java", "-Xmx512m", "-jar", "/app.jar", "--server.port=${PORT}"]