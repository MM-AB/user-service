FROM openjdk:19

WORKDIR /opt

ENV PORT 8080

EXPOSE 8080

ADD target/user-service.jar user-service.jar

ENTRYPOINT [ "java", "-jar", "user-service.jar" ]