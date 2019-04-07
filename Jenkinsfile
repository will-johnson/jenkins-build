#!/usr/bin/env groovy Jenkinsfile。
pipeline{
    agent any
    //定义仓库地址
    environment {
        REPOSITORY="https://github.com/will-johnson/jenkins-build"
        DOCKERNAME="seen-app"
    }
    stages{
        stage('Clean'){
            steps{
                echo "cleaning directory"
                //清空当前目录
                deleteDir()
            }
        }
        stage('Getcode'){
            steps {
                echo "start fetch code from git:${REPOSITORY}"
                //拉去代码
                git "${REPOSITORY}"
            }
        }
        stage('Build'){
            steps{
                echo "building..."
                sh '''
                    pwd
                    mvn clean package -Dmaven.test.skip=true
                    echo "packaged..."
                '''
            }
        }
        stage('Docker'){
            steps{

                echo "stop docker..."
                echo "the docker name is ${DOCKERNAME}"
                sh '''
                    stop_name=`docker ps | awk '$2=="seen-app"{print $2}'`
                    if [ $stop_name ]
                    then 
                        docker stop $stop_name
                    fi
                '''
                echo "rm docker container..."
                sh '''
                    container_name=`docker ps -a | awk '$2=="seen-app" {print $2}'`
                    if [ $container_name ]
                    then 
                        docker rm $container_name
                    fi
                '''
                echo "rmi docker image..."
                sh '''
                    image_name=`docker images | awk '$1=="seen-app" {print $1}'`
                    if [ $image_name ]
                    then 
                        docker rmi $image_name
                    fi
                '''

                echo "kill port 8888"
                sh '''
                port=8888
                echo "停止8888服务中..."
                if lsof -i:$port;then
                  kill -9 $(lsof -i:$port -t)
                  echo "关闭8888端口服务"
                fi
                '''

                echo "generating dockerfile"
                sh '''
                    pwd
                    cat>Dockerfile<<EOF
FROM openjdk:8
MAINTAINER Will Johnson
LABEL app="seen-app" version="0.0.1" by="johnson"
COPY ./target/demo-0.0.1-SNAPSHOT.jar seen-app.jar
CMD java -jar seen-app.jar
EOF
                '''
                echo "building docker image"
                sh '''
                    docker build -t seen-app .    
                '''
                echo "built docker image [SUCCESS]"
                echo "starting project"
                sh '''
                    docker run --name seen-app -p 8888:8888 -d seen-app
                '''
            }
        }
        stage("Test"){
            steps{
                echo "make test"
            }
        }
        stage("Deploy"){
            steps{
                echo "make publish"
            }
        }
    }
    post {
        always {
            echo 'This will always run'
        }
    }
}