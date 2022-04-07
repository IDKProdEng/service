pipeline {
    agent any
    environment {
        DOCKER_PASSWORD = credentials("Alexandru1")
    }

    stages {
        stage('Build & Test') {
            steps {
                sh './gradlew clean build'
            }
        }
    }
}