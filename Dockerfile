FROM eclipse-temurin:17-jdk AS builder
WORKDIR /workspace
COPY . .

RUN apt-get update && apt-get install -y dos2unix && dos2unix gradlew && chmod +x gradlew

RUN ./gradlew bootJar --no-daemon --info -x test 2>&1 || true
RUN test -f build/libs/*.jar || (echo "JAR_NOT_FOUND:_check_logs_above" && exit 1)

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /workspace/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx256m", "-jar", "app.jar"]
