# Stage 1: Build the multi-module project
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy root pom and module poms/sources needed for the build
COPY pom.xml .
COPY yapper-shared ./yapper-shared
COPY yapper-server ./yapper-server

# Build only the server module and its dependencies
RUN mvn clean package -pl yapper-server -am -DskipTests

# Stage 2: Minimal runtime image
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the server JAR from the build stage
COPY --from=build /app/yapper-server/target/*.jar app.jar

# Expose port 5000
EXPOSE 5000

# Run the server
ENTRYPOINT ["java", "-jar", "app.jar"]