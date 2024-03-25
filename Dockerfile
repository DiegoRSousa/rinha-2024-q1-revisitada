FROM openjdk:21
VOLUME /tmp
ADD ./target/rinha-2024-q1-revisitada-0.0.1-SNAPSHOT.jar rinha.jar
ENTRYPOINT ["java","-jar","/rinha.jar"]
