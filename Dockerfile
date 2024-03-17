# Use the official Gradle image to create a build artifact
FROM gradle:7.4.2-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test --no-daemon

# Use OpenJDK 17 for the runtime
FROM openjdk:17-slim
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/spring-boot-application.jar"]
