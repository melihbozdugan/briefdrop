# Stage 1: Build
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /workspace
COPY . .

# Fix Windows line endings (CRLF -> LF) + make executable
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

# Build with memory limits for Render free tier (512 MB)
ENV GRADLE_OPTS="-Dorg.gradle.jvmargs=-Xmx300m"
RUN ./gradlew bootJar --no-daemon --no-build-cache -Dorg.gradle.jvmargs=-Xmx300m -x test

# Stage 2: Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /workspace/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx256m", "-jar", "app.jar"]
