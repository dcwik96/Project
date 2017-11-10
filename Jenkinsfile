pipeline {
    agent none
    stages {
        stage('Back-end') {
            agent {
                docker { image 'maven:3-alpine' }
            }
            steps {
                sh 'pwd && cd backend && mvn spring-boot:start && cd ..'
            }
        }
        stage('Front-end') {
            agent {
                docker { image 'node:7-alpine' }
            }
            steps {
                sh 'cd /var/jenkins_home/workspace/IleDasz/frondend && node run dev && cd .. '
            }
        }
    }
}
