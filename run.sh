#!/bin/bash

# Bash for Linux
clear
sudo systemctl restart docker.socket docker.service
docker rm -f $(docker ps -a -q)
docker build -t motafelipe/spring-api .
# We must need to run the container in background.
docker run -p 3000:8080  -d --name spring-api motafelipe/spring-api