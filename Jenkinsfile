pipeline {
    agent ant
    stages {
        stage('build') {
            steps {
                sh 'mvn spring-boot:start'
            }
        }
    }
}