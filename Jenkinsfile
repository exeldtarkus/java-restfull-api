pipeline {
  agent any
  
  options { 
        disableConcurrentBuilds() 
        buildDiscarder(logRotator(numToKeepStr: "5", daysToKeepStr: "5"))
        timeout(time: 1, unit: 'HOURS')
    }
        
    stages {    
        stage('build ms-moservice-content-service.') {
            steps {
                build job: 'ms-moservice-content-service', wait: true
            }
        }
        
    }
}
