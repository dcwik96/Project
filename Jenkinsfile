pipeline {
    agent none
    stages {
        stage('Build') {
           	
            steps {
            	 try {
            	 	notifyStarted()
                 	sh 'mvn --projects backend spring-boot:start'
                 	notifySuccess()
                 } catch(e) {
                 	currentBuild.result = "FAILED"
                 	notyfiFailed()
                 	throw e
                 }
            }
        }
    }
}

def notifyStarted() {
            	slackSend(color : "#FFFF00", message: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
            }
def notifySuccess() {
	slackSend(color: '#00FF00', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER' (${env.BUILD_URL})")
}

def notifyFailed() {
	slackSend(color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER' (${env.BUILD_URL})")
}

