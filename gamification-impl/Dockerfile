FROM openjdk:15

COPY target/gamification-impl-1.0.0.jar /usr/src/gamification.jar

## wait until mariadb is up
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /wait
RUN chmod +x /wait

CMD /wait && java -jar /usr/src/gamification.jar