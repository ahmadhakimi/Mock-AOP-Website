## Use a base image with Java and Maven installed
#FROM maven:3.9.4-eclipse-temurin-21 AS build
#
## Set the working directory in the container
#WORKDIR /app
#
## Copy the pom.xml and source code into the container
#COPY pom.xml .
#COPY src ./src
#
## Build the application
#RUN mvn clean package -DskipTests
#
## Use a smaller base image for the runtime
#FROM eclipse-temurin:21-jre
#
## Set the working directory
#WORKDIR /app
#
## Copy the JAR file from the build stage into the runtime image
#COPY --from=build app/target/crud-0.0.1-SNAPSHOT.jar app.jar
#
## Specify the command to run your application
#ENTRYPOINT ["java", "-jar", "app.jar"]
#
## Expose the application port
#EXPOSE 8080
#
#LABEL authors="VirtualBag"
