# ****************************************************************
# Commands to build and run ( Execute it in the shell )
# ****************************************************************
#   JAVA_HOME=$(dirname $( readlink -f $(which java) )) &&
#   JAVA_HOME=$(realpath "$JAVA_HOME"/../) &&
#   export JAVA_HOME &&
#   mvn clean &&
#   ./mvnw install &&
#   docker build -t myorg/myapp . &&
#   docker run -p 3000:8080 myorg/myapp
#*****************************************************************

# In this case we are using .jar insted of .war
# docker build -t myorg/myapp . (Never uncomment this line)

# To run in local Host
FROM openjdk:12-alpine
COPY target/*.jar /backoffice.jar
CMD ["java", "-jar", "/backoffice.jar"]
ENTRYPOINT [ "sh", "-c", "java -jar backoffice.jar" ]

# To run in Ec2 instance
#FROM openjdk:12-alpine
#COPY *.jar /backoffice.jar
#CMD ["java", "-jar", "/backoffice.jar"]
#ENTRYPOINT [ "sh", "-c", "java -jar backoffice.jar" ]