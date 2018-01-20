pipeline {
    agent any
    tools {
        maven 'Maven'
        jdk 'OpenJDK 8'
        nodejs 'Node'
    }
    stages {
        stage('Build') {
            steps {
                 	sh mvn --projects backend spring-boot:start
            }
        }
        stage('Test') {
            sh mvn test
        }
    }

    post {
        always {
            notifyStarted()
        }
        failure {
            notifyFailed()
        }
        success {
            notifySuccess()
        }
    }
}

def notifyStarted() {
    slackSend(color : "#FFFF00", message: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
            }
def notifySuccess() {
	slackSend(color: '#00FF00', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
}

def notifyFailed() {
	slackSend(color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
}
