FROM openjdk:8-jdk-alpine
MAINTAINER ian.ilminmoon@gmail.com
#ARG JAR_FILE=build/libs/*.jar
ARG JAR_FILE=*.jar

COPY ${JAR_FILE} app.jar

#ENTRYPOINT ["run.sh"]
ENTRYPOINT ["java", "-jar", "/app.jar"]