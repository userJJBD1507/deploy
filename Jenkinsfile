pipeline {
    agent {
        kubernetes {
            yaml """
apiVersion: v1
kind: Pod
metadata:
  labels:
    app: jenkins-pipeline
spec:
  containers:
    - name: docker
      image: docker:latest
      securityContext:
        privileged: true
"""
        }
    }
    environment {
        GITHUB_CREDENTIALS_ID = 'github-credentials-id' // ID для GitHub в Jenkins
        GHCR_CREDENTIALS_ID = 'ghcr-credentials-id'     // ID для GHCR в Jenkins
        IMAGE_NAME = 'ghcr.io/frolovinr/idservice'
        REPO_URL = 'https://github.com/userJJBD1507/deploy.git'
        DOCKER_FILE = 'Dockerfile'
        REPORTS_DIR = "reports"
    }
    stages {
        
        
        stage('Get build time') {
            steps {
                script {
                    env.BUILD_TAG = "${env.BUILD_TIMESTAMP}"
                }
            }
        }
        stage('Checkout') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: env.GITHUB_CREDENTIALS_ID, usernameVariable: 'GITHUB_USER', passwordVariable: 'GITHUB_TOKEN')]) {
                        sh """
                        git config --global credential.helper store
                        echo "https://${GITHUB_USER}:${GITHUB_TOKEN}@github.com" > ~/.git-credentials
                        git config --global credential.helper 'store --file ~/.git-credentials'
                        """
                    }
                }
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/master']], // Замените 'main' на вашу ветку
                    userRemoteConfigs: [[
                        url: env.REPO_URL, // Укажите ваш репозиторий
                        credentialsId: env.GITHUB_CREDENTIALS_ID
                    ]]
                ])
            }
        }
        stage('Build Docker Image') {
            steps {
                container('docker') {
                    script {
                        sh """
                        docker build . -f ${DOCKER_FILE} -t ${env.IMAGE_NAME}:${env.BUILD_TIMESTAMP}
                        """
                    }
                }
            }
        }
        stage('Tag Docker Image') {
            steps {
                container('docker') {
                    script {
                        sh """
                        docker tag ${env.IMAGE_NAME}:${env.BUILD_TIMESTAMP} ${env.IMAGE_NAME}:latest
                        """
                    }
                }
            }
        }

        stage('Lint Dockerfile') {
            
            steps {
                container('docker') {
                    script {
                        sh '''
                        mkdir -p $REPORTS_DIR
                        docker run --rm -i hadolint/hadolint < Dockerfile | tee $REPORTS_DIR/hadolint_report.txt
                        '''
                    }
                }
            }
        }

        stage('Analyze Image with Dive') {
            steps {
                container('docker') {
                    script {
                        sh '''
                        docker run --rm -v /var/run/docker.sock:/var/run/docker.sock wagoodman/dive $IMAGE_NAME:latest --ci > $REPORTS_DIR/dive_report.txt || true
                        '''
                    }
                }
            }
        }

        stage('Scan for Vulnerabilities with Trivy') {
            steps {
                container('docker') {
                    script {
                        sh '''
                        docker run --rm -v /var/run/docker.sock:/var/run/docker.sock -v $(pwd):/workspace aquasec/trivy image --no-progress --format table -o /workspace/$REPORTS_DIR/trivy_report.txt $IMAGE_NAME:latest
                        '''
                    }
                }
            }
        }

        stage('Check Best Practices with Dockle') {
            steps {
                container('docker') {
                    script {
                        sh '''
                        docker run --rm -v /var/run/docker.sock:/var/run/docker.sock -v $(pwd):/workspace goodwithtech/dockle --exit-code 0 --format markdown -o /workspace/$REPORTS_DIR/dockle_report.md $IMAGE_NAME:latest
                        '''
                    }
                }
            }
        }

        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: 'reports/*', fingerprint: true
            }
        }
    

        stage('Push to GHCR') {
            steps {
                container('docker') {
                    script {
                        withCredentials([usernamePassword(credentialsId: env.GHCR_CREDENTIALS_ID, usernameVariable: 'GHCR_USER', passwordVariable: 'GHCR_TOKEN')]) {
                            sh """
                            echo "${GHCR_TOKEN}" | docker login ghcr.io -u "${GHCR_USER}" --password-stdin
                            docker push ${env.IMAGE_NAME}:${env.BUILD_TIMESTAMP}
                            """
                        }
                    }
                }
            }
        }
        
        stage('Push to GHCR latest') {
            steps {
                container('docker') {
                    script {
                        withCredentials([usernamePassword(credentialsId: env.GHCR_CREDENTIALS_ID, usernameVariable: 'GHCR_USER', passwordVariable: 'GHCR_TOKEN')]) {
                            sh """
                            echo "${GHCR_TOKEN}" | docker login ghcr.io -u "${GHCR_USER}" --password-stdin
                            docker push ${env.IMAGE_NAME}:latest
                            """
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
