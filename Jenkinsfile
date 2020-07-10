pipeline {
   agent any
   stages {
      stage('Building ...') {
         steps {
            powershell label: '', script: 'mvnw clean package jib:dockerBuild'
         }
      }
   }
}
