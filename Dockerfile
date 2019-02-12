FROM openjdk:8-jdk-alpine
LABEL maintainer="abhijeetshukla07@outlook.com"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/parkingLotApi-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} parkingLotApi-0.0.1-SNAPSHOT.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/parkingLotApi-0.0.1-SNAPSHOT.jar"]