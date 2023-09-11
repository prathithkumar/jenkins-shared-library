def lintChecks() {

            sh "echo starting lintChecks for ${COMPONENT}"
            sh "mvn checkstyle:check || true"
            sh "echo lintChecks completed for ${COMPONENT}"
}
def call() {
    pipeline {
        agent any 
        stages {
            stage('Lint Checks') {
                steps {
                    script {
                        lintChecks()
                    }
                }
            }
            
            stage('Generating Artifacts') {
                steps {
                    sh "echo Generating Artifacts for $COMPONENT"
                    sh "mvn clean package"

                    }
                }
            }
        }
    }
