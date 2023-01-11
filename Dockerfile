FROM openjdk:11

ADD . /app
RUN set -ex \
  && cd /app \
  && ./gradlew build

EXPOSE 8080
WORKDIR /app
CMD ./gradlew bootRun
