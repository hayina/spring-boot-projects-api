pipeline {
   agent any
   stages {
      stage('Check branch') {
         steps {
            echo "$GIT_BRANCH"
         }
      }
      stage('Building ...') {
         steps {
            powershell label: '', script: 'mvn clean package'
         }
      }
   }
}
