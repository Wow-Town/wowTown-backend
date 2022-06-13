pipeline {
    agent any
    environment{
        dockerhub=credentials('docker-access-credential')
    }
    stages {
        stage('Gradle Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }
        stage('Docker Image Build') {
            when{ branch "main"}
            steps {
                echo 'Build Start!!!'
                sh 'docker build -t wowtown_backend:0.1 ./'
            }
        }
        stage('Docker Hub Push') {
            when{ branch "main"}
            steps {
                echo 'Image Push Start!!!'
                sh 'docker image tag wowtown_backend:0.1 devconf5296/wowtown_backend:0.1'
                sh 'echo $dockerhub_PSW | docker login -u $dockerhub_USR --password-stdin'

                sh 'docker push devconf5296/wowtown_backend:0.1'

            }
        }
        stage('SSH transfer') {
            steps([$class: 'BapSshPromotionPublisherPlugin']) {
                sshPublisher(
                    continueOnError: false, failOnError: true,
                    publishers: [
                        sshPublisherDesc(
                            configName: "wowtown_backend",//Jenkins 시스템 정보에 사전 입력한 서버 ID
                            verbose: true,
                            transfers: [
                                sshTransfer(
                                    sourceFiles: "docker-compose.yml,wowtown.sh", //전송할 파일
                                    removePrefix: "", //파일에서 삭제할 경로가 있다면 작성
                                    remoteDirectory: "/", //배포할 위치
                                    execCommand: "cd ~/deploy; chmod +x wowtown.sh; sh wowtown.sh" //원격지에서 실행할 커맨드
                                )
                            ]
                        )
                    ]
                )
            }
        }
    }
}