pipeline {
    agent any

 tools {
        maven 'Maven_latest'
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
		
		stage('Backend SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') { // Replace with your SonarQube server name
                    dir('backend/backend') {
                        bat 'mvn sonar:sonar'
                    }
                }
            }
        }
        stage('Quality Gate') {
            steps {
                script {
					//sleep(60)       only used a bypass of webhook This delay gives SonarQube additional time to complete the analysis and update the Quality Gate status.
                    echo "Checking Quality Gate status..."
                    timeout(time: 20, unit: 'MINUTES') {
                        def qg = waitForQualityGate()
                        echo "Quality Gate status: ${qg.status}"
                        if (qg.status != 'OK') {
                            error "Pipeline aborted due to quality gate failure: ${qg.status}"
                        }
                    }
                }
            }
        }
		
        stage('Backend Deploy') {
            steps {
                dir('backend') {
                    bat '''
                    docker build -t backend .
		    docker rm -f backend || true
                    docker run -d -p 8081:8080 --name backend \
                        -e DB_URL=${DB_URL} \
                        -e DB_USERNAME=${DB_USERNAME} \
                        -e DB_PASSWORD=${DB_PASSWORD} \
                        backend
                    '''
                }
            }
        }
        stage('Frontend Build') {
            steps {
                dir('frontend/lms-frontend') {
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
                    docker build -t lms-frontend .
		    docker rm -f lms-frontend || true
                    docker run -d -p 4200:80 --name lms-frontend lms-frontend
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
