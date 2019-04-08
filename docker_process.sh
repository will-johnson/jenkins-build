#!/usr/bin/env bash
DOCKER_NAME="seen-app"

echo "stop docker ${DOCKER_NAME}"
stop_name=`docker ps | awk '$2=="'${DOCKER_NAME}'"{print $2}'`
if [ $stop_name ]
then 
    docker stop $stop_name
fi

echo "rm docker container..."
container_name=`docker ps -a | awk '$2=="'${DOCKER_NAME}'" {print $2}'`
if [ $container_name ]
then 
    docker rm $container_name
fi

echo "rmi docker image..."
image_name=`docker images | awk '$1=="'${DOCKER_NAME}'" {print $1}'`
if [ $image_name ]
then 
    docker rmi $image_name
fi

echo "kill port 8888"
port=8888
echo "停止8888服务中..."
if lsof -i:$port;then
kill -9 $(lsof -i:$port -t)
echo "关闭8888端口服务"
fi

echo "generating dockerfile"
pwd
cat>Dockerfile<<EOF
FROM openjdk:8
MAINTAINER Will Johnson
LABEL app="${DOCKER_NAME}" version="0.0.1" by="johnson"
COPY ./target/demo-0.0.1-SNAPSHOT.jar ${DOCKER_NAME}.jar
CMD java -jar ${DOCKER_NAME}.jar
EOF

echo "building docker image"
docker build -t ${DOCKER_NAME} .
echo "built docker image [SUCCESS]"