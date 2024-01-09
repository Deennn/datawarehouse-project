FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/DataWarehouse-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "/app/DataWarehouse-0.0.1-SNAPSHOT.jar" ]