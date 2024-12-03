# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the compiled JAR file from the local machine to the Docker container
COPY target/Travelo.jar app.jar

# Set the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expose port 8081 (as per your application.properties)
EXPOSE 8081
