pipeline {
  agent any
  options {
    buildDiscarder(logRotator(numToKeepStr: '3'))
  }
  environment {
    DOCKERHUB_CREDENTIALS = credentials('dockerhub')
    DB_URL = credentials('fino-db-url')
    DB_USERNAME = credentials('fino-db-username')
    DB_PASSWORD = credentials('fino-db-password')
    JWT_SECRET = credentials('fino-jwt-secret')
    repository = "kimjunhee020327/fino-server"
    dockerImage = ''
  }
  stages {
    stage('Gradle Build') {
      steps {
        sh 'chmod +x ./gradlew'
        // Skip tests to avoid requiring database connection during build
        sh './gradlew clean build -x test'
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
        sh '''docker run -d -p 8080:8080 --name test_repository \
          -e SPRING_DATASOURCE_URL="${DB_URL}" \
          -e SPRING_DATASOURCE_USERNAME="${DB_USERNAME}" \
          -e SPRING_DATASOURCE_PASSWORD="${DB_PASSWORD}" \
          -e SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver \
          -e SPRING_JPA_HIBERNATE_DDL_AUTO=update \
          -e SPRING_JPA_SHOW_SQL=true \
          -e SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true \
          -e SPRING_JWT_SECRET="${JWT_SECRET}" \
          -e SPRING_PROFILES_ACTIVE=prod \
          $repository:$BUILD_NUMBER'''
      }
    }
  }
  post {
    always {
      node('') {
        sh 'docker logout'
      }
    }
  }
}