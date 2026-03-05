# Stage 1: Build using JDK 25 directly
FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app
# Install Maven manually since the combined image is "Not Found"
RUN apk add --no-cache maven
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Important: This service uses port 8081
EXPOSE 8081

# Use Xmx512m to stay within Render's Free Tier memory limits
ENTRYPOINT ["java", "-Xmx512m", "-jar", "/app/app.jar", "--server.port=${PORT}"]