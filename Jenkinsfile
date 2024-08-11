pipeline {
    agent any

    tools {
        maven 'M3'
    }

    environment {
        DOCKER_TAG = '1.8'
        DOCKER_IMAGE = 'zmianowy'
    }

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'jenkins-github-pat', branch: 'master', url: 'https://github.com/fresco0700/SpringSec5x.git'
            }
        }

        stage('Build without Tests') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image as Tar with Jib') {
            steps {
                script {
                    sh "mvn compile jib:buildTar"
                }
            }
        }
    }

    post {
        success {
            script {
                    sh 'mv target/jib-image.tar target/${DOCKER_IMAGE}:${DOCKER_TAG}.tar'
                }
            archiveArtifacts 'target/*.jar'
            archiveArtifacts "target/${DOCKER_IMAGE}:${DOCKER_TAG}.tar"
        }

        failure {
            echo 'Build failed.'
        }
    }
}
