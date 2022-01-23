pipeline {
    agent any
    tools{
        maven '3.8.4'
    }
      stages {
        stage('Initializing') {
            steps {
                echo 'company-management-system service'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -version'
                sh 'mvn clean install -DskipTests=true'
            }
        }
        stage('Docker') {
            steps {
                script {
                         docker.build("company-management-system.jar")
                        }
            }
        }
        stage('ECR') {
            steps {
               script {
                         docker.withRegistry("https://387115656091.dkr.ecr.ap-south-1.amazonaws.com/", "ecr:ap-south-1:AWS-credentials") {
                         docker.image("company-management-system.jar").push()
                        }
               }
            }
        }
        stage('Release') {
            steps {
                echo 'Release..'
            }
        }
    }
}
