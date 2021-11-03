FROM adoptopenjdk:11-jre-hotspot
MAINTAINER Aleksandr Kliushin
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} receiver-0.0.1-SNAPSHOT-jar-with-dependencies.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/receiver-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]