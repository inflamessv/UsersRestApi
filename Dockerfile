FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/UsersRestApi-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "UsersRestApi-0.0.1-SNAPSHOT.jar"]