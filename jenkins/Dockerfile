FROM jenkins/jenkins:lts-jdk11
#USER root

#RUN chown -R 1000:1000 /var/jenkins_home/
#RUN add-apt-repository ppa:webupd8team/java
RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install -y openssh-client
#RUN apt-get install java-8-oracle
#RUN echo export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 >> ~/.bashrc
#RUN echo export PATH="$PATH:$JAVA_HOME/bin" >> ~/.bashrc
#RUN source ~/.bashrc
#USER 1001
#RUN javac -version
