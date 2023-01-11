FROM openjdk:11

RUN set -ex \
  && apt-get update \
  && apt-get install flip

ADD . /app
RUN set -ex \
  && cd /app \
  && flip -u ./gradlew \
  && ./gradlew build

EXPOSE 8080
WORKDIR /app
CMD ./gradlew bootRun
