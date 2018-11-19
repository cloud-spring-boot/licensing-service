FROM openjdk:8-jdk
MAINTAINER Maksym Stepanenko <stepanenkomaksi@gmail.com>

########################################################################################################################
# Alpine
########################################################################################################################
#RUN apk update && apk upgrade && apk add netcat-openbsd bash tcpdump curl

########################################################################################################################
# Debian
########################################################################################################################
RUN apt-get update --fix-missing
RUN apt-get install -y tcpdump netcat curl git g++ make
RUN apt-get install openjdk-8-dbg

RUN mkdir -p /usr/local/licensing-service
RUN mkdir -p /usr/local/licensing-service/pcap

########################################################################################################################
# add async-profiler
########################################################################################################################
WORKDIR /usr/local/licensing-service
RUN git clone https://github.com/jvm-profiling-tools/async-profiler.git
WORKDIR /usr/local/licensing-service/async-profiler
RUN make

########################################################################################################################
WORKDIR /usr/local/licensing-service
ADD @project.build.finalName@.jar /usr/local/licensing-service/
ADD run.sh run.sh
ADD loop.sh loop.sh
RUN chmod +x run.sh loop.sh
HEALTHCHECK --start-period=30s --interval=5s CMD curl http://localhost:8080/health || exit 1

CMD ./run.sh
#CMD ./loop.sh


