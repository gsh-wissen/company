pipeline {
    agent any
    tools{
        maven '3.8.4'
    }
      stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
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