# (1) build the app with maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build .jar file ,skip the tests
RUN mvn clean package -DskipTests

# (2) run the application with jdk
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Copy the .jar file from (1)
COPY --from=build /app/target/*.jar app.jar

# Expose 8080 port
EXPOSE 8080

# run command
ENTRYPOINT ["java", "-jar", "app.jar"]