# Imagen base para el contenedor de compilación
FROM maven:3.9.0-eclipse-temurin-17 as builder
WORKDIR /WindService
COPY WindService/pom.xml /WindService/
RUN mvn -B clean verify --fail-never
COPY /WindService/src /WindService/src
RUN mvn -B package -DskipTests

# Imagen base para el contenedor de la aplicación
FROM eclipse-temurin:17-jdk
WORKDIR /usr/src/app/
COPY --from=builder /WindService/target/*.jar /usr/src/app/
RUN curl -LJO https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh && chmod +x /usr/src/app/wait-for-it.sh
EXPOSE 8084
CMD [ "java", "-jar", "windservice-0.0.1-SNAPSHOT.jar" ]