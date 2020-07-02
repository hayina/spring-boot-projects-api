FROM openjdk:14-alpine

WORKDIR /app


ARG JAR_FILE=target/projects-api-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} api-server.jar

ENTRYPOINT ["java", "-jar", "api-server.jar"]

