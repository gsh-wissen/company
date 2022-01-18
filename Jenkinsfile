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
                sh 'docker build -f Dockerfile -t company-management-system.jar .'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying..'
            }
        }
        stage('Release') {
            steps {
                echo 'Release..'
            }
        }
    }
}
