pipeline {
    agent none
    stages {
        stage('Back-end') {
            agent {
                docker { image 'maven:3-alpine' }
            }
            steps {
                sh 'mvn --version'
            }
        }
        stage('Front-end') {
            agent {
                docker { image 'node:7-alpine' }
            }
            steps {
                sh 'node -v'
            }
        }
        stage('Build') {
                agent any
                steps {
                    sh 'mvn install && mvn --projects backend spring-boot:start'
                }
        }
    }
}
