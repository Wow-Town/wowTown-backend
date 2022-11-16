FROM base
ARG branch_name

RUN  apk update \
  && apk upgrade \
  && apk add --update openjdk11 tzdata curl unzip bash \
  && rm -rf /var/cache/apk/*

COPY ./build/libs/ ./deploy/wowtown_backend/

ENTRYPOINT ["java","-Xmx2048m", "-jar", "/deploy/wowtown_backend/wowTown-backen-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod","--spring.mail.password=${MAIL_PASSWORD}","--cloud.aws.credentials.accessKey=${AWS_ACCESS_KEY_ID}","--cloud.aws.credentials.secretKey=${AWS_SECRETE_KEY}","--server.ssl.key-store=${SSL_PKCS12_STORE}","--server.ssl.key-store-password=${SSL_PKCS12_PASSWORD}"]

