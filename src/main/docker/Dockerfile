FROM openjdk:8-jre-alpine
MAINTAINER Maksym Stepanenko <stepanenkomaksi@gmail.com>
RUN  apk update && apk upgrade && apk add netcat-openbsd bash tcpdump curl
RUN mkdir -p /usr/local/licensing-service
RUN mkdir -p /usr/local/licensing-service/pcap
ADD @project.build.finalName@.jar /usr/local/licensing-service/
ADD run.sh run.sh
RUN chmod +x run.sh
HEALTHCHECK --start-period=30s --interval=5s CMD curl http://localhost:8080/health || exit 1
CMD ./run.sh


