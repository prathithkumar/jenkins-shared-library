def sonarChecks() {
    stage('Sonar Checks') {
    sh "echo starting Sonar Checks for ${COMPONENT}"
        // sh "sonar-scanner -Dsonar.host.url=http://${SONAR_URL}:9000/  $ARGS -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_CRED_USR} -Dsonar.password=${SONAR_CRED_PSW}"
        // sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh"
        // sh "bash quality-gate.sh ${SONAR_CRED_USR} ${SONAR_CRED_PSW} ${SONAR_URL} ${COMPONENT}"
    sh "echo Sonar Checks ${COMPONENT} are completed"
    }
}



def lintChecks() {
    stage('lint Checks') {
        if(env.APPTYPE == "nodejs") {
            sh "echo Installing JSlist"
            sh "npm i jslint"
            sh "echo starting lintChecks for ${COMPONENT}"
            sh "node_modules/jslint/bin/jslint.js server.js || true"
            sh "echo lintChecks completed for ${COMPONENT}"
        }
        else if(env.APPTYPE == "maven") {
        sh "echo starting lintChecks for ${COMPONENT}"
        sh "mvn checkstyle:check || true"
        sh "echo lintChecks completed for ${COMPONENT}"
        }
        else if(env.APPTYPE == "pylint") {
        sh "echo starting lintChecks for ${COMPONENT}"
        sh "echo lintChecks completed for ${COMPONENT}"
        }
        else {
            sh "Lint Checks for frontend are in progress"
        }
    }    
}


def testCases() {
    stage('Test Cases') {
        def stages = [:]

        stages["Unit Testing"] = {
            echo "Unit Testing in Progress"
            echo "Unit Testing in Completed"
        }
        stages["Integration Testing"] = {
            echo "Integration Testing in Progress"
            echo "Integration Testing in Completed"
        }
        stages["Functional Testing"] = {
            echo "Functional Testing in Progress"
            echo "Functional Testing in Completed"            

        }
        parallel(stages)
    }
}



