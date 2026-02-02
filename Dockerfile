# Use an official JDK runtime as a parent image
FROM azul/zulu-openjdk:21

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from your target folder into the container
# Note: Ensure you run './mvnw package' first to generate this JAR
COPY target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]