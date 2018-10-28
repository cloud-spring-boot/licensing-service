#!/bin/bash
echo "********************************************************"
echo "Waiting for the Eureka to start on port $EUREKASERVER_PORT"
echo "********************************************************"
while ! `nc -z eureka $EUREKASERVER_PORT `; do sleep 3; done
echo ">>>>>>>>>>>> Eureka has started"

echo "********************************************************"
echo "Waiting for the configuration server to start on port $CONFIGSERVER_PORT"
echo "********************************************************"
while ! `nc -z config-service $CONFIGSERVER_PORT `; do sleep 3; done
echo ">>>>>>>>>>>> Configuration Server has started"

echo "********************************************************"
echo "Waiting for the database server to start on port $DATABASESERVER_PORT"
echo "********************************************************"
while ! `nc -z database $DB_PORT`; do sleep 3; done
echo ">>>>>>>>>>>> Database Server has started"

echo "********************************************************"
echo "Waiting for the kafka server to start on port  $KAFKA_SERVER_PORT"
echo "********************************************************"
while ! `nc -z kafka $KAFKA_SERVER_PORT`; do sleep 10; done
echo "******* Kafka Server has started"

########################################################################################################################
# Monitor HTTP traffic on port 8080 including request and response headers and message body.
########################################################################################################################
nohup tcpdump -A -s 0 'tcp port 8080 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)' \
-w /usr/local/licensing-service/pcap/licensing.pcap &

########################################################################################################################
echo "********************************************************"
echo "Starting licensing-service "
echo "********************************************************"
java \
-Djava.security.egd=file:/dev/./urandom \
-Deureka.client.serviceUrl.defaultZone=$EUREKASERVER_URI \
-Dspring.cloud.config.uri=$CONFIGSERVER_URI \
-Dspring.profiles.active=$PROFILE \
-Dspring.cloud.stream.kafka.binder.zkNodes=$KAFKA_SERVER_URI \
-Dspring.cloud.stream.kafka.binder.brokers=$ZK_SERVER_URI \
-jar /usr/local/licensing-service/@project.build.finalName@.jar