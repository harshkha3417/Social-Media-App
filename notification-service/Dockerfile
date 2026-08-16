FROM maven:3.9.4-eclipse-temurin-21-alpine

WORKDIR /app

# Copy files explicitly
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src ./src

# Ensure the executable dot-slash format is used
CMD ["./mvnw", "spring-boot:run"]
