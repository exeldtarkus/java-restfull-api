pipeline {
  agent any

    environment {
        appsName = "ms-moservice-content-service"
        registry = "registry-intl.ap-southeast-5.aliyuncs.com/adira-dcoe/moservice-dev-${appsName}"
        dockerImage = ''
        environmentName = 'dev'
        gitUrl = 'https://iutomo@bitbucket.org/dcoedev/content-service.git'
        gitUrlBase = 'https://iutomo@bitbucket.org/dcoedev/base-service.git'
        gitBranchBase = 'dev'
        gitBranch = 'dev'
        jarName = 'content-service-1.0.0.jar'
    }    
    
    tools { 
        maven 'mvn-3.6.3' 
        jdk 'jdk11' 
    }

    options { 
        disableConcurrentBuilds() 
        buildDiscarder(logRotator(numToKeepStr: "5", daysToKeepStr: "5"))
        timeout(time: 1, unit: 'HOURS')
    }
    
    stages {   

        stage('Cloning Base Repository') { 
            steps{
                sh 'rm -rf *'
                git branch: gitBranchBase, 
                credentialsId: 'iutomo',
                url: gitUrlBase
            }
        }
        
        stage('Compiling Base Codes') {
            steps{
                sh 'mvn clean install'
            }
        }
 
        stage('Cloning Repository') { 
            steps{
                sh 'rm -rf *'
                git branch: gitBranch, 
                credentialsId: 'iutomo',
                url: gitUrl
            }
        }
        
        stage('Compiling Codes') {
            steps{
                //sh 'cp -R /opt/deployment/${appsName}/* .'
                sh 'mvn clean;mvn clean install'
                sh 'cp target/${jarName} /opt/deployment/${appsName}/'
                sh 'rm -rf *'
            }
        }
        
        stage('Building Docker Image and Push To Registry') {
            steps{
                script {
                    sh 'cp -R /opt/deployment/${appsName}/* .'
                    dockerImage = docker.build registry + ":AUTOMATIC_$BUILD_NUMBER"
                    dockerImage.push()
                    sh 'docker rmi -f $registry:AUTOMATIC_$BUILD_NUMBER'
                    //sh 'docker images -q -f dangling=true | xargs --no-run-if-empty docker rmi -f'
                }
            }
        }
    
        stage('Triggering Kuberentes') {
            steps{
                sh 'sed -i \"s%registry-intl.ap-southeast-5.aliyuncs.com/adira-dcoe/moservice-dev-$appsName:latest%registry-intl.ap-southeast-5.aliyuncs.com/adira-dcoe/moservice-dev-$appsName:AUTOMATIC_$BUILD_NUMBER%g\" ${appsName}-deployment.yml'
                sh '/usr/local/bin/kubectl --kubeconfig=/var/lib/jenkins/.kube/config_dev_momobil replace --force -f ${appsName}-deployment.yml'
                //sh '/usr/local/bin/kubectl --kubeconfig=/var/lib/jenkins/.kube/config_uat_momobil replace --force -f /opt/deployment/${appsName}.yml'
            }
        }
    }
}
