pipeline {
    agent any

    stages {
        stage('Check') {
            steps {
                git branch: 'develop',credentialsId:'0-shingo', url:'https://github.com/Spharos-final-project-WOOYANO/User'
            }
        }
        stage('Build'){
            steps{
                sh '''
                    cd User
                    chmod +x ./gradlew
                    ./gradlew build -x test
                '''
            }
        }
        stage('DockerSize'){
            steps {
                sh '''
                    cd server
                    docker stop User-Service || true
                    docker rm User-Service || true
                    docker rmi User-Service-Img || true
                    docker build -t User-Service-Img:latest .
                '''
            }
        }
        stage('Deploy'){
            steps{
                sh 'docker run -d --name User-Service -p 8080:8000 User-Service-Img'
            }
        }
    }
}
