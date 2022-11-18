FROM openjdk:17
COPY /target .
EXPOSE 8000
CMD ["java", "-jar", "hack-sovcom-1.0.jar"]