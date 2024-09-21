FROM openjdk:21-jdk
VOLUME /tmp
ARG JAR_FILE=build/libs/redis_deep_dive-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} redis.jar
ENTRYPOINT ["java","-jar","/redis.jar"]