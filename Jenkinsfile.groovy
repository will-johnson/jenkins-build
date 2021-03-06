#!/usr/bin/env groovy Jenkinsfile.groovy
pipeline{
    agent any
    //定义仓库地址
    environment {
        REPOSITORY="https://github.com/will-johnson/jenkins-build"
        def PROJECT_PATH="/root/.jenkins/workspace/pipeline_test"
    }
    stages{
        stage('Clean'){
            steps{
                echo "cleaning directory"
                deleteDir()
            }
        }
        stage('Getcode'){
            steps {
                echo "start fetch code from git:${REPOSITORY}"
                git "${REPOSITORY}"
            }
        }
        stage('Build'){
            steps{
                sh '''
                    mvn clean package -Dmaven.test.skip=true
                '''
                echo "packaged..."
            }
        }
        stage('Docker'){
            steps{
                sh 'chmod 744 docker_process.sh'
                sh PROJECT_PATH+'/docker_process.sh'
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
                sh '''
                    docker run --name seen-app -p 8888:8888 -d seen-app
                '''
            }
        }
    }
    post {
        always {
            echo 'This will always run'
        }
    }
}