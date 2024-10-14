FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/client_person_service-0.0.1-SNAPSHOT.jar /app/clientservice.jar

COPY src/main/resources/schema.sql /app/schema.sql

ENTRYPOINT ["java", "-jar", "/app/clientservice.jar"]

EXPOSE 8081