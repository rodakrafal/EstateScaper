FROM maven:3.9.9-amazoncorretto-21-alpine AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/EstateCrawler*.jar /app/EstateCrawler.jar
ENTRYPOINT ["java", "-jar", "EstateCrawler.jar"]
