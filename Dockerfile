#FROM openjdk:8-alpine
# #Required for starting application up.
#RUN apk update && apk add /bin/sh
#RUN mkdir -p /opt/app
#ENV PROJECT_HOME /opt/app
#COPY target/spring-boot-mongo-1.0.jar $PROJECT_HOME/spring-boot-mongo.jar
#WORKDIR $PROJECT_HOME
#CMD ["java" ,"-jar","./spring-boot-mongo.jar"]
# Build Stage
FROM maven:3.9.4-eclipse-temurin-17-alpine AS builder

# Set working directory for building
WORKDIR /app

# Copy pom.xml and download dependencies (for layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:17-jre-alpine

# Set working directory for running
WORKDIR /opt/app

# Copy the built JAR from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
