# Create a container that only builds the software to be installed into the master container
FROM maven:3.6-jdk-8-alpine as builder

# Add source files into builder
ADD . /build
WORKDIR /build
RUN mvn -DskipTests clean package

# Create a container with needed dependencies for running the app
FROM openjdk:8u181-jdk-alpine3.8 as runtime
EXPOSE 8080

#copy jar from previous stage
COPY --from=builder /build/target/coding-challenge*.jar coding-challenge.jar

CMD java -jar coding-challenge.jar
