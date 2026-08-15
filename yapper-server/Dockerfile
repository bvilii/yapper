# Use an official Maven image with Java 21 to build the server
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy root configuration and all modules so Maven's reactor is happy
COPY pom.xml .
COPY yapper-shared ./yapper-shared
COPY yapper-server ./yapper-server
COPY yapper-client ./yapper-client

# Build only the server module and its dependencies
RUN mvn clean package -pl yapper-server -am -DskipTests

# Stage 2: Minimal runtime image
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/yapper-server/target/*.jar app.jar

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "app.jar"]