FROM jenkins/jenkins:lts-jdk11
RUN apt-get update &&\
    apt-get upgrade -y &&\
    apt-get install -y openssh-client
RUN sudo apt-get install openjdk-8-jdk
RUN echo export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 >> ~/.bashrc
RUN echo export PATH="$PATH:$JAVA_HOME/bin" >> ~/.bashrc
RUN source ~/.bashrc
RUN javac -version