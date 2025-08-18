pipeline {
  agent any
  options {
    buildDiscarder(logRotator(numToKeepStr: '3'))
  }
  environment {
    DOCKERHUB_CREDENTIALS = credentials('dockerhub')
    repository = "kimjunhee020327/fino-server"
    dockerImage = ''
  }
  stages {
    stage('Gradle Build') {
      steps {
        sh 'chmod +x ./gradlew'
        sh './gradlew clean build'
      }
    }
    stage('Docker Build') {
      steps {
        script {
            dockerImage = docker.build repository + ":$BUILD_NUMBER"
        }
      }
    }
    stage('Login') {
      steps {
        sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
      }
    }
    stage('Push') {
      steps {
        sh 'docker push $repository:$BUILD_NUMBER'
      }
    }
    stage('Pull') {
      steps {
        sh 'docker pull $repository:$BUILD_NUMBER'
      }
    }
    stage('stop prev container') {
      steps {
        sh 'docker stop test_repository || true'
        sh 'docker rm test_repository || true'
      }
    }
    stage('Run') {
      steps {
        sh 'docker run -d -p 8080:8080 --name test_repository $repository:$BUILD_NUMBER'
      }
    }
  }
  post {
    always {
      sh 'docker logout'
    }
  }
}