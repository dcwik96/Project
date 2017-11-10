pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
            image 'node:6-alpine' 
            args '-p 3000:3000'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'cd fronted && npm install && cd ..'
                sh 'mvn install && mvn --projects backend spring-boot:run'
            }
        }
    }
}
