FROM maven:3.9.4-eclipse-temurin-21-alpine

WORKDIR /app


COPY .mvn/ .mvn
COPY mvnw pom.xml ./


COPY src ./src

CMD ["./mvnw", "spring-boot:run"]
