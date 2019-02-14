# Build
FROM maven:3-jdk-11-slim as builder

VOLUME /root/.m2
RUN mkdir /build
WORKDIR /build
COPY . .
RUN mvn package -DskipTests

# Main
FROM openjdk:11-slim
COPY --from=builder /build/target/*-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]