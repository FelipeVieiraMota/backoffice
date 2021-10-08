#!/bin/bash

# Bash for Linux

clear

echo "Pushing files to the server ec2"

cd ~/workspace/Canada/AWS-KEYS/

pwd

scp -i "BackOffice.pem" ~/workspace/Canada/backoffice/target/backoffice-0.0.1-SNAPSHOT.jar <?>:/home/ubuntu
echo "Copied jar file from local machine to ec2 instance"
echo "~/workspace/Canada/backoffice/target/backoffice-0.0.1-SNAPSHOT.jar"

scp -i "BackOffice.pem" ~/workspace/Canada/backoffice/Dockerfile <?>:/home/ubuntu
echo "Copied latest 'Dockerfile' file from local machine to ec2 instance"
echo "~/workspace/Canada/backoffice/Dockerfile"

scp -i "BackOffice.pem" ~/workspace/Canada/backoffice/run.sh <?>:/home/ubuntu
echo "Copied latest 'run.sh' file from local machine to ec2 instance"
echo "~/workspace/Canada/backoffice/run.sh"

echo "Finished"