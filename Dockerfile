FROM adoptopenjdk:17-jdk-hotspot
COPY target/employee-reports-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
