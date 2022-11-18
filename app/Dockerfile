FROM maven:3.8-openjdk-17-slim AS build

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src

RUN unset MAVEN_CONFIG && ./mvnw -B package

FROM openjdk:17.0.2-jdk-slim AS release

COPY --from=build target/hack-sovcom-1.0.jar .
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "hack-sovcom-1.0.jar"]