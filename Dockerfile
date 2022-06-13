FROM alpine:3.14
ARG branch_name

RUN  apk update \
  && apk upgrade \
  && apk add --update openjdk11 tzdata curl unzip bash \
  && rm -rf /var/cache/apk/*

COPY ./build/libs/ ./deploy/wowtown_backend/

ENTRYPOINT ["java", "-jar", "/deploy/wowtown_backend/wowTown-backen-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]

