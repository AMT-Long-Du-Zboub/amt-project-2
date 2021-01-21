FROM maven:3.6.3-openjdk-15

COPY . /usr/src/gamification-impl
WORKDIR /usr/src/gamification-impl

RUN mvn clean package

CMD java -jar target/gamification-impl-1.0.0.jar