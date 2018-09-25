#!/bin/sh
echo "********************************************************"
echo "Waiting for the configuration server to start on port $CONFIGSERVER_PORT"
echo "********************************************************"
while ! `nc -z config-service $CONFIGSERVER_PORT `; do sleep 3; done
echo ">>>>>>>>>>>> Configuration Server has started"

echo "********************************************************"
echo "Starting licensing-service "
echo "********************************************************"
java \
-Dspring.cloud.config.uri=$CONFIGSERVER_URI \
-Dspring.profiles.active=$PROFILE \
-jar /usr/local/licensing-service/@project.build.finalName@.jar