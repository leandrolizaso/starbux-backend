FROM openjdk:8-jdk-alpine
COPY target/starbux-1.0-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app.jar"]