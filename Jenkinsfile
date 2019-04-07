#!/usr/bin/env groovy Jenkinsfile。
pipeline{
    agent any
    //定义仓库地址
    environment {
        REPOSITORY="https://github.com/will-johnson/jenkins-build"
    }
    stages{
        stage('clean'){
            steps{
                echo "cleaning directory"
                //清空当前目录
                deleteDir()
            }
        }
        stage('getcode'){
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
                    mvn clean package -Dmaven.test.skip=true -Pdev
                    echo "packaged...
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