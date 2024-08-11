FROM openjdk:17-jre
WORKDIR /app
COPY app-ZMIANOWY.jar /app/app-ZMIANOWY.jar
COPY application.properties /app/application.properties
CMD ["java", "-jar", "/app/app-ZMIANOWY.jar", "--spring.config.location=file:/app/application.properties"]
