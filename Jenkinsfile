pipeline {

   agent any

   stages {

      stage('Check branch') {

         steps {
            echo "$GIT_BRANCH"
         }

         post {
            success {
               echo "Check branch success !!"
            }
         }
      }

   }

}
