FROM openjdk:8
VOLUME /temp
EXPOSE 8085
ADD ./target/ms-retire-0.0.1-SNAPSHOT.jar retire-service.jar
ENTRYPOINT ["java","-jar","/retire-service.jar"]