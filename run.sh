#!/bin/bash

# Bash for Linux
clear
systemctl restart docker.socket docker.service
docker build -t motafelipe/spring-api .
docker run -p 3000:8080  --name spring-api motafelipe/spring-api