def lintChecks() {

            sh "echo starting lintChecks for ${COMPONENT}"
            sh "pylint *.py || true"
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
        }
    }
}
    