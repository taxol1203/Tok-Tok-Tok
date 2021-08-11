pipeline {
	agent none
	options { skipDefaultCheckout(true) }
	stages {
		stage('Build and Test') {
			agent {
				docker {
					image 'maven:3-alpine'
					args '-v /root/.m2:/root/.m2'
				}
			}
			options { skipDefaultCheckout(false) }
			steps {
				sh 'mvn -B -DskipTests -f ./backend_source/pom.xml clean package'
			}
		}
		stage('Docker build') {
			agent any
			steps {
				sh 'docker build -t frontend:latest ./vue'
				sh 'docker build -t spring:latest ./spring'
			}
		}
		stage('Docker run') {
			agent any
			steps {
				sh 'docker ps -f name=frontend -q \
					| xargs --no-run-if-empty docker container stop'
				sh 'docker ps -f name=spring -q \
					| xargs --no-run-if-empty docker container stop'
				sh 'docker container ls -a -f name=frontend -q \
					| xargs -r docker container rm'
				sh 'docker container ls -a -f name=spring -q \
					| xargs -r docker container rm'
				sh 'docker images -f "dangling=true" -q \
					| xargs -r docker rmi'
                sh 'docker run -d --name spring -p 8080:8080 --network s05p12d204_net spring:latest'
				sh 'docker run -d --name frontend -p 80:80 -p 443:443 -v /home/ubuntu/cert:/usr/share/nginx/html -v /home/ubuntu/S05P12D204/vue/nginx/conf:/etc/nginx/conf.d --network s05p12d204_net frontend:latest '

			}
		}
	}
}