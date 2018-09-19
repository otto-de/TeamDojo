FROM openjdk:8-jdk

COPY . /app
WORKDIR /app
RUN git clone --no-checkout https://github.com/iteratec/slab-dojo .tmpdir
RUN mv .tmpdir/.git .git
RUN rm -rf .tmpdir/
RUN ./gradlew bootWar -Pprod --info --stacktrace

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JHIPSTER_SLEEP=0 \
    JAVA_OPTS=""

CMD echo "The application will start in ${JHIPSTER_SLEEP}s..." && \
    sleep ${JHIPSTER_SLEEP} && \
    java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app/teamdojo.war

EXPOSE 8080

RUN mv /app/build/libs/*.war /app/teamdojo.war