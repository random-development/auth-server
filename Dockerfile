FROM maven:3.5-jdk-8-alpine AS build 
COPY /src /usr/src/auth-serv/src
COPY pom.xml /usr/src/auth-serv
COPY Dockerfile /usr/src/auth-serv
RUN mvn -f /usr/src/auth-serv/pom.xml clean install

FROM openjdk:8-jre-alpine
COPY --from=build /usr/src/auth-serv/target/demo-0.0.1-SNAPSHOT.jar auth-serv.jar
ENTRYPOINT ["java","-jar","/auth-serv.jar"]