#!/bin/bash

##################
# Bash for Linux #
##################

######################################
# 1) Cleaning *.jar                  #
######################################
clear
echo "1) Cleaning *.jar"
echo
mvn clean
echo
echo "Cleaning *.jar finished"
echo
######################################

# -- END -- #

######################################
# 2) Packing *.jar                   #
######################################
echo "2) Packing *.jar"
echo
mvn package
echo
echo "Packing *.jar finished"
echo
######################################

# -- END -- #

######################################
# 3) Pushing files to the server ec2 #
######################################
echo "3) Pushing files to the server ec2"
cd ~/workspace/Canada/AWS-KEYS/
pwd
ls
echo
################################

# -- END -- #

################################################################
# 4) Coping jar file from local machine to ec2 instance        #
################################################################
echo "4) Coping jar file from local machine to ec2 instance"
scp -i "BackOfficeStudents.pem" ~/workspace/Canada/backoffice/target/backoffice-0.0.1-SNAPSHOT.jar ubuntu@ec2-3-87-208-55.compute-1.amazonaws.com:/home/ubuntu/target
echo "Copied jar file from local machine to ec2 instance"
echo "~/workspace/Canada/backoffice/target/backoffice-0.0.1-SNAPSHOT.jar"
echo
################################################################

# -- END -- #

################################################################
# 4) Coping jar file from local machine to ec2 instance        #
################################################################
echo "Coping latest 'Dockerfile' file from local machine to ec2 instance"
scp -i "BackOfficeStudents.pem" ~/workspace/Canada/backoffice/Dockerfile ubuntu@ec2-3-87-208-55.compute-1.amazonaws.com:/home/ubuntu
echo "Copied latest 'Dockerfile' file from local machine to ec2 instance"
echo "~/workspace/Canada/backoffice/Dockerfile"
echo
################################################################

# -- END -- #

################################################################
# 4) Coping jar file from local machine to ec2 instance        #
################################################################
echo "Coping latest 'run.sh' file from local machine to ec2 instance"
scp -i "BackOfficeStudents.pem" ~/workspace/Canada/backoffice/run.sh ubuntu@ec2-3-87-208-55.compute-1.amazonaws.com:/home/ubuntu
echo "Copied latest 'run.sh' file from local machine to ec2 instance"
echo "~/workspace/Canada/backoffice/run.sh"
echo
################################################################

echo "Local Process Finished - SUCCESS"

echo "Starting Process in EC2 side"
echo "Executing ./run.sh in EC2 side"
ssh -i "BackOfficeStudents.pem" ubuntu@ec2-3-87-208-55.compute-1.amazonaws.com '/home/ubuntu/run.sh'

echo "Finished Process in EC2 side - SUCCESS"