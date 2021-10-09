#!/bin/bash

# Bash for Linux
clear
sudo systemctl restart docker.socket docker.service
docker rm -f $(docker ps -a -q)
docker build -t motafelipe/spring-api .
docker run -p 3000:8080  --name spring-api motafelipe/spring-api