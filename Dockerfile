FROM docker.target.com/tap/alpine-adoptopenjdk:11 as build

RUN apk update && apk add gradle

ADD . /src
WORKDIR /src
RUN gradle build

FROM docker.target.com/tap/alpine-adoptopenjdk:11

COPY --from=build /src/build/libs/gs-reactive-rest-service-*.jar /resilience4j-hystrix-mapper.jar

USER 65534
EXPOSE 8080

CMD ["java", "-jar", "/resilience4j-hystrix-mapper.jar"]
