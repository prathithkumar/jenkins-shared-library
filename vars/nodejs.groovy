def lintChecks() {
            sh "echo Installing JSlist"
            sh "npm i jslint"
            sh "echo starting lintChecks for ${COMPONENT}"
            sh "node_modules/jslint/bin/jslint.js server.js || true"
            sh "echo lintChecks completed for ${COMPONENT}"
}

def call() {
    node {
        common.lintChecks
    }
}

/* Declarative Pipeline
def call() {
    pipeline {
        agent any 
        environment {
            SONAR_URL   = "172.31.25.77"
            NEXUS_URL   = "172.31.18.210"
            SONAR_CRED  = credentials('SONAR_CRED')
            NEXUS_CRED  = credentials('NEXUS_CRED')
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
            stage('Check the Release') {
                when {
                    expression { env.TAG_NAME != null }
                }
                steps {
                    script {
                       env.UPLOAD_STATUS=sh(returnStdout: true, script: "curl -L -s http://${NEXUS_URL}:8081/service/rest/repository/browse/${COMPONENT} | grep ${COMPONENT}-${TAG_NAME}.zip || true")
                       print UPLOAD_STATUS
                    }
                }
            }  
            stage('Generating Artifacts') {
                when {
                    expression { env.TAG_NAME != null }
                    expression { env.UPLOAD_STATUS == "" }
                }
                steps {
                    sh "echo Generating Artifacts...."
                    sh "npm install"
                    sh "zip ${COMPONENT}-${TAG_NAME}.zip node_modules server.js"
                    sh "ls -ltr"

                }
            }
            stage('Uploading the Artifacts') { 
                when {
                    expression { env.TAG_NAME != null }
                    expression { env.UPLOAD_STATUS == "" }
                }
                steps {
                    sh '''
                       echo Uplaoding ${COMPONENT} artifact to nexus
                       curl -v -u ${NEXUS_CRED_USR}:${NEXUS_CRED_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://${NEXUS_URL}:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip
                       echo Uploading ${COMPONENT} artifact to nexus is completed
                    '''
                }
            }            
        }
    }
}
