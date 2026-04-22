FROM amazoncorretto:25

COPY target/img-base-64-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]