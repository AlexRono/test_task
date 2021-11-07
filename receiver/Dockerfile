FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
COPY /pom.xml /build/
COPY ./receiver /build/child/
WORKDIR /build/child/
RUN mvn clean package

FROM adoptopenjdk:11-jre-hotspot
MAINTAINER Aleksandr Kliushin
WORKDIR /app
COPY --from=MAVEN_BUILD /build/child/target/receiver-1.0-SNAPSHOT.jar /app/
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/app/receiver-1.0-SNAPSHOT.jar"]