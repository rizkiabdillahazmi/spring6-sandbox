#build stage
#FROM openjdk:21-jdk-slim AS build
#WORKDIR /app
#COPY . .
## COPY --chown=gradle:gradle . /app
##RUN gradle clean build --no-daemon --stacktrace
## Package stage
##FROM openjdk:21-jdk-slim
#COPY /app/build/libs/demo-0.0.1-SNAPSHOT.jar /usr/local/lib/demo-0.0.1-SNAPSHOT.jar
#EXPOSE 8181
#ENTRYPOINT exec java -jar /usr/local/lib/demo-0.0.1-SNAPSHOT.jar

# Use the OpenJDK 21 image as a base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from your local machine to the container
COPY /build/libs/demo-0.0.1-SNAPSHOT.jar /usr/local/lib/demo-0.0.1-SNAPSHOT.jar

# Expose the port your application listens on (if it's 8181)
EXPOSE 8282

# Define the command to run your application
ENTRYPOINT ["java", "-Xmx384m", "-jar", "/usr/local/lib/demo-0.0.1-SNAPSHOT.jar"]