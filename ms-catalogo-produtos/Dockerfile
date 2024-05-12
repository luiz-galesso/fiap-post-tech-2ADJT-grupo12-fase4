FROM maven:latest as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:latest 
VOLUME /tmp
COPY --from=build /app/target/ms-gerenciamento-produtos-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","ms-gerenciamento-produtos-0.0.1-SNAPSHOT.jar"]