# Base image for builder container with Maven and JDK 21
FROM maven:3.9.0-eclipse-temurin-17 as builder
WORKDIR /project
COPY Planner/pom.xml /project/
RUN mvn -B clean compile --fail-never
COPY Planner/src /project/src
RUN mvn -B package -DskipTests

# Base image for the application container with JDK 21
FROM eclipse-temurin:17-jdk

# Set the working directory
WORKDIR /usr/src/app/


# Optionally, include the wait-for-it script
RUN curl -LJO https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh \
    && chmod +x /usr/src/app/wait-for-it.sh

# Copy the packaged application from the builder stage
COPY --from=builder /project/target/*.jar /usr/src/app/

# Expose the application port
EXPOSE 8080

# Define the entrypoint
CMD ["java", "-jar", "eolopark-0.0.1-SNAPSHOT.jar"]

