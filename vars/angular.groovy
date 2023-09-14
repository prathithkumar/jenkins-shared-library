def call() {
    node {
        git branch: 'main', url: "https://github.com/prathithkumar/${COMPONENT}.git"
        common.lintChecks()
        env.ARGS="-Dsonar.sources=."
        env.NEXUS_URL   = "172.31.18.210"
        common.sonarChecks()
        common.testCases()
        common.artifacts()
    }
}