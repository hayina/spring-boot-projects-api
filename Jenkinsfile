pipeline {
   agent any
   stages {
      stage('Compiling') {
         steps {
            powershell 'mvn clean test-compile -DskipTests'
         }
      }
      stage('Testing') {
         steps {
            powershell 'mvn surefire:test'
         }
      }
      stage('Packaging') {
         steps {
            powershell 'mvn jar:jar spring-boot:repackage'
         }
      }
      stage('Build docker image') {
         steps {
            powershell 'mvn jib:dockerBuild'
         }
      }
   }
}
