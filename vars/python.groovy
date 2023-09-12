def lintChecks() {

            sh "echo starting lintChecks for ${COMPONENT}"
            sh "pylint *.py || true"
            sh "echo lintChecks completed for ${COMPONENT}"
}
def call() {
    pipeline {
        agent any 
        environment {
            SONAR_URL   = "172.31.25.77"
            SONAR_CRED  = credentials('SONAR_CRED')
        }
        stages {
            stage('Lint Checks') {
                steps {
                    script {
                        lintChecks()
                    }
                }
            }
            stage('Sonar Checks') {
                steps {
                    script {
                        env.ARGS="-Dsonar.sources=."
                        common.sonarChecks ()
                        }
                    }
                }
            stage('Generating Artifacts') {
                steps {
                    sh "echo artifacts Generation Completed"
                }
            }
        }
    }
}
    