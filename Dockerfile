# Stage 1: Build the application
FROM gradle:8.4-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app


COPY --from=build /app/build/libs/ParseqLabTest-all.jar app.jar
COPY --from=build /app/src/main/resources /app/resources

ENV DATA_FILE_PATH=/app/resources/data/clinvar_20220430.aka.gz
ENV INDEX_FILE_PATH=/app/resources/data/clinvar_20220430.aka.gz.tbi

ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "app.jar"]