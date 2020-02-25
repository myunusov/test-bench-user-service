FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine as builder
RUN apk update && \
    apk add su-exec tzdata libpq postgresql-client postgresql postgresql-contrib && \
    rm -rf /var/cache/apk/*

ENV POSTGRES_USER postgres
ENV POSTGRES_DB postgres
ENV POSTGRES_PASSWORD postgres
ENV PGDATA /var/lib/postgresql/data

ADD . /src
RUN ["chmod", "-R", "777", "/src"]

USER postgres
RUN initdb -A trust -D /var/lib/postgresql/data
COPY ./postgres/postgresql.conf /var/lib/postgresql/data/postgresql.conf
RUN pg_ctl start -D /var/lib/postgresql/data -l /var/lib/postgresql/log.log && cd ./src && ./mvnw package -DskipTests

FROM alpine:3.10.3 as packager
RUN apk --no-cache add openjdk11-jdk openjdk11-jmods
ENV JAVA_MINIMAL="/opt/java-minimal"
# build minimal JRE
RUN /usr/lib/jvm/java-11-openjdk/bin/jlink \
    --verbose \
    --add-modules \
        java.base,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument \
    --compress 2 --strip-debug --no-header-files --no-man-pages \
    --release-info="add:IMPLEMENTOR=radistao:IMPLEMENTOR_VERSION=radistao_JRE" \
    --output "$JAVA_MINIMAL"

FROM alpine:3.10.3
LABEL maintainer="Maksim Iunusov maksim@iunusov.ru"
ENV JAVA_HOME=/opt/java-minimal
ENV PATH="$PATH:$JAVA_HOME/bin"
COPY --from=packager "$JAVA_HOME" "$JAVA_HOME"
COPY --from=builder /src/target/user-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]