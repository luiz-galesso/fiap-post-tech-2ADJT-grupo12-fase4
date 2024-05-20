FROM maven:latest as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
#RUN mvn clean package

FROM openjdk:latest 
VOLUME /tmp
COPY --from=build /app/target/ms-catalogo-produtos-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","ms-catalogo-produtos-0.0.1-SNAPSHOT.jar"]