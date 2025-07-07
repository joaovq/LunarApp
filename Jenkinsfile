pipeline {
  agent {
    docker {
      image 'androidsdk/android-30'
    }

  }
  stages {
    stage('Checkout') {
      agent any
      steps {
        git(branch: 'master', url: 'https://github.com/joaovq/LunarApp')
      }
    }

  }
  environment {
    branch = 'master'
  }
}