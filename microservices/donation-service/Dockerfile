FROM openjdk:17-jdk-slim as build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/donation-service-0.0.1-SNAPSHOT.jar donation-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "donation-service.jar"]
