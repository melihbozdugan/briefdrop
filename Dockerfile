FROM gradle:8-jdk17 AS builder
WORKDIR /workspace

COPY build.gradle settings.gradle ./
COPY gradle gradle/
COPY src src/

RUN gradle bootJar --no-daemon --no-build-cache -x test

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /workspace/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx256m", "-jar", "app.jar"]
