FROM adoptopenjdk:11-jre-hotspot
MAINTAINER Aleksandr Kliushin
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} sender-0.0.1-SNAPSHOT-jar-with-dependencies.jar
ENTRYPOINT ["java","-jar","/sender-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]