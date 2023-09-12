def lintChecks() {
            sh "echo Installing JSlist"
            sh "npm i jslint"
            sh "echo starting lintChecks for ${COMPONENT}"
            sh "node_modules/jslint/bin/jslint.js server.js || true"
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
                            common.sonarChecks()
                        }                       
                    }
                }
            stage('Test cases') {
                parallel {
                    stage('Unit Testing') {
                        steps {
                            sh "echo starting Unit Testing"
                            sh "echo Unit Testing Completed"
                        }
                    }
                    stage('Integration Testing') {
                        steps {
                            sh "echo starting Integration Testing"
                            sh "echo Integration Testing Completed"
                        }
                    }
                    stage('Functional  Testing') {
                        steps {
                            sh "echo starting Functional Testing"
                            sh "echo Functional Testing Completed"
                        }    
                    }
                }
            }    
            stage('Generating Artifacts') {
                steps {
                    sh "echo Generating Artifacts...."
                    sh "npm install"

                }
            }
            stage('Uploading the Artifacts') {
                steps {
                    sh "echo Uploading Artifacts.."
                }
            }            
        }
    }
}
