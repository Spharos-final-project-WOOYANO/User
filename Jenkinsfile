pipeline {
    agent any
    stages {
        stage('Check') {
            steps {
                git branch: 'develop',credentialsId:'0-shingo', url:'https://github.com/Spharos-final-project-WOOYANO/User'
            }
        }
	stage('Secret-File Download') {
	    steps {
	        withCredentials([
		    file(credentialsId:'Wooyano-Secret-File', variable: 'secret')
		    ])
	        {
		    sh "cp \$secret ./src/main/resources/application-secret.yml"
		}	    
  	    }
	}
        stage('Build'){
            steps{
                script {
                    sh '''
                        pwd
                        chmod +x ./gradlew
                        ./gradlew build
                    '''
                    
                }
                    
            }
        }
        stage('DockerSize'){
            steps {
                sh '''
                    docker stop user-service || true
                    docker rm user-service || true
                    docker rmi user-service-img || true
                    docker build -t user-service-img:latest .
                '''
            }
        }
        stage('Deploy'){
            steps{
                sh 'docker run --network spharos-network -e EUREKA_URL="${EUREKA_URL}" -d --name user-service user-service-img'
            }
        }
    }
}

