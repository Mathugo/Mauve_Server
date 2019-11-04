#!/bin/bash 

sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java10-installer -y
sudo apt-get install oracle-java10-set-default -y
sudo apt install default-jre -y
sudo apt install default-jdk -y

sudo apt-get install mvn 
