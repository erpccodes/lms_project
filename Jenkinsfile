pipeline {
    agent any

 tools {
        maven 'Maven 3.9.7'
    }

    environment {
        DB_URL = 'jdbc:mysql://localhost:3306/lms_db'
        DB_USERNAME = 'root'
        DB_PASSWORD = 'root'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/erpccodes/lms_project.git'
            }
        }
        stage('Backend Build') {
            steps {
                dir('backend/backend') {
                    bat 'mvn clean install'
                }
            }
        }
        stage('Backend Test') {
            steps {
                dir('backend/backend') {
                    bat 'mvn test'
                }
            }
        }
        stage('Backend Package') {
            steps {
                dir('backend/backend') {
                    bat 'mvn package'
                }
            }
        }
        stage('Backend Deploy') {
            steps {
                dir('backend/backend') {
                    bat '''
                    docker build -t lms_backend .
                    docker run -d -p 8080:8080 --name lms_backend \
                        -e DB_URL=${DB_URL} \
                        -e DB_USERNAME=${DB_USERNAME} \
                        -e DB_PASSWORD=${DB_PASSWORD} \
                        lms_backend
                    '''
                }
            }
        }
        stage('Frontend Build') {
            steps {
                dir('frontend') {
                    bat '''
                    npm install
                    npm run build
                    '''
                }
            }
        }
        stage('Frontend Deploy') {
            steps {
                dir('frontend') {
                    bat '''
                    docker build -t lms_frontend .
                    docker run -d -p 4200:80 --name lms_frontend lms_frontend
                    '''
                }
            }
        }
    }
    post {
        always {
            archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
