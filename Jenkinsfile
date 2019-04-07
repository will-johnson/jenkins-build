#!/usr/bin/env groovy Jenkinsfile。
pipeline{
    agent any
    //定义仓库地址
    environment {
        REPOSITORY="https://github.com/will-johnson/jenkins-build"
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
                echo "generating dockerfile"
                sh '''
                    pwd
                    cat>seen-app<<EOF
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