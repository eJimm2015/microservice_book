FROM openjdk:14
ADD target/microservice-book.jar /microservice-book.jar
EXPOSE 8001
ENTRYPOINT ["java","-jar","/microservice-book.jar"]