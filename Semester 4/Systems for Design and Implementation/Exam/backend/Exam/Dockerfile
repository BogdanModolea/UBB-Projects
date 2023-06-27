FROM openjdk:19-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ./target/spring-boot-aws.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]