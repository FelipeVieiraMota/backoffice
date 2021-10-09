#!/bin/bash

# Bash for Linux #
clear

echo "1) Cleaning *.jar"
echo
mvn clean
echo
echo "Cleaning *.jar finished"
echo

echo "2) Packing *.jar"
echo
mvn package
echo
echo "Packing *.jar finished"
echo

echo "Pushing files to the server ec2"
cd ~/workspace/Canada/AWS-KEYS/
pwd
ls
echo

scp -i "BackOfficeStudents.pem" ~/workspace/Canada/backoffice/target/backoffice-0.0.1-SNAPSHOT.jar ubuntu@ec2-3-87-208-55.compute-1.amazonaws.com:/home/ubuntu/target
echo "Copied jar file from local machine to ec2 instance"
echo "~/workspace/Canada/backoffice/target/backoffice-0.0.1-SNAPSHOT.jar"
echo

scp -i "BackOfficeStudents.pem" ~/workspace/Canada/backoffice/Dockerfile ubuntu@ec2-3-87-208-55.compute-1.amazonaws.com:/home/ubuntu
echo "Copied latest 'Dockerfile' file from local machine to ec2 instance"
echo "~/workspace/Canada/backoffice/Dockerfile"
echo

scp -i "BackOfficeStudents.pem" ~/workspace/Canada/backoffice/run.sh ubuntu@ec2-3-87-208-55.compute-1.amazonaws.com:/home/ubuntu
echo "Copied latest 'run.sh' file from local machine to ec2 instance"
echo "~/workspace/Canada/backoffice/run.sh"
echo

echo "Finished"