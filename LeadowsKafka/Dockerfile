# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built Spring Boot jar into the container
COPY target/*.jar app.jar

# Expose the desired port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
