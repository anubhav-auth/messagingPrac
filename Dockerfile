FROM amazoncorretto:17.0.12

WORKDIR /app

COPY target/messagingPrac-0.0.1-SNAPSHOT.jar /app/messagingPrac-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/messagingPrac-0.0.1-SNAPSHOT.jar"]