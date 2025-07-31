# ightweight OpenJDK image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the jar file
COPY build/libs/*.jar app.jar

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]