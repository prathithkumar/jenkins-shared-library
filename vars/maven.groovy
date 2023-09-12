def lintChecks() {

            sh "echo starting lintChecks for ${COMPONENT}"
            sh "mvn checkstyle:check || true"
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
            
            stage('Code Compile') {
                steps {
                    sh "echo Generating Artifacts for $COMPONENT"
                    sh "mvn clean compile"

                    }
                }
            stage('Sonar Checks') {
                steps {
                    sh "echo starting Sonar Checks for ${COMPONENT}"
                    sh "sonar-scanner -Dsonar.host.url=http://${SONAR_URL}:9000/ -Dsonar.java.binaries=target/ -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_CRED_USR} -Dsonar.password=${SONAR_CRED_PSW}"
                    sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh"
                    sh "bash quality-gate.sh ${SONAR_CRED_USR} ${SONAR_CRED_PSW} ${SONAR_URL} ${COMPONENT}"
                    sh "echo Sonar Checks ${COMPONENT} are completed"
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
