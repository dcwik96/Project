pipeline {
    agent none
    stages {
        stage('Back-end') {
            
            agent {
                docker { image 'maven:3-alpine' }
            }
            steps {
                dir("IleDasz/backend") {
                    sh 'mvn spring-boot:start'
                }
            }
        }
        stage('Front-end') {
            agent {
                docker { image 'node:7-alpine' }
            }
            steps {
                dir("IleDasz/frontend") {
                    sh 'npm install && npm run-script build'
                }
            }
        }
    
    }
}
