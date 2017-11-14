pipeline {
    agent none
    stages {
        stage('Build') {
            
            agent any
            steps {
                 sh 'mvn --project backend spring-boot:start'
            }
        }
    }
}
