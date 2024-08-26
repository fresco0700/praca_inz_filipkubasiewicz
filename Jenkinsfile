pipeline {
    agent any

    tools {
        maven 'M3'
    }

    environment {
        DOCKER_TAG = '2.0'
        DOCKER_IMAGE = 'zmianowy-inz'
        SSH_HOST = '<adres-ip-maszyny>'
        REMOTE_DIR = '<katalog-na-serwerze>'
        SSH_PORT = '<port ssh>'
    }

    stages {
        stage('Checkout credentials') {
            steps {
                // Repozytorium do skonfigurowania  np. git credentialsId: 'jenkins-github-pat', branch: 'master', url: 'https://github.com/fresco0700/praca_inz_filipkubasiewicz.git'
            }
        }

        stage('Build application') {
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

        stage('Transfer Docker Image to backend server') {
            steps {
                sh """
                scp -P ${SSH_PORT} target/jib-image.tar jenkins@${SSH_HOST}:${REMOTE_DIR}/
                """
            }
        }
        stage('Load Docker Image and Deploy') {
            steps {
                sh """
                ssh -p ${SSH_PORT} jenkins@${SSH_HOST} 'docker load -i ${REMOTE_DIR}/jib-image.tar && docker-compose -f ${REMOTE_DIR}/docker-compose.yaml up -d'
                """
            }
        }
    }

    post {
        success {
            script {
                
                sh ''
                }
            archiveArtifacts 'target/*.jar'
            archiveArtifacts "target/jib-image.tar"
        }

        failure {
            echo 'Build failed.'
        }
    }
}
