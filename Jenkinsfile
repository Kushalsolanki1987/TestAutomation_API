pipeline {
    agent any

    stages {
        stage ('Compile Stage') {

            steps {
                withMaven(maven : 'maven_3_5_0') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage ('Testing Stage') {

            steps {
                withMaven(maven : 'maven_3_5_0') {
                    sh 'mvn test'
                }
            }
        }


        stage ('Cucumber Reports') {
            steps {
                 cucumber buildStatus: “UNSTABLE”,
			fileIncludePattern: "**/cucumber-report.json",
			jsonReportDirectory: 'target'
        }
    }
}}