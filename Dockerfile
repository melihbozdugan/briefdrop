# Stage 1: Build
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /workspace
COPY . .

RUN chmod +x gradlew

RUN ./gradlew bootJar --no-daemon --no-build-cache -Dorg.gradle.jvmargs="-Xmx300m" -x test --stacktrace

# Stage 2: Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /workspace/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx256m", "-jar", "app.jar"]
