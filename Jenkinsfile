pipeline {
    agent none
    stages {
        stage('Back-end') {
            
            agent {
                docker { image 'maven:3-alpine' }
            }
            steps {
                 sh 'cd backend && mvn spring-boot:start'
            }
        }
        stage('Front-end') {
            agent {
                docker { image 'node:7-alpine' }
            }
            steps {
                
                    sh 'echo "Frontend bulid success"'
              
            }
        }
    
    }
}
