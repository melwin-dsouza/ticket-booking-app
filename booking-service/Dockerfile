# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .

# Download the project dependencies
RUN mvn dependency:go-offline -B

# Copy the entire project into the container
COPY . .

# Build the project and skip tests
RUN mvn clean install -DskipTests

# Stage 2: Package the application
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/booking-service-0.0.1-SNAPSHOT.jar booking-service.jar

# Expose the port your application will run on
EXPOSE 8082

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/booking-service.jar"]
