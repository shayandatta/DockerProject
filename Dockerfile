FROM openjdk:8-nanoserver
EXPOSE 9091
ADD target/docker-springboot.jar docker-springboot.jar
ENTRYPOINT ["java","-jar","/docker-springboot.jar"]