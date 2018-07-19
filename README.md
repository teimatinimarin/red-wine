# red-wine
This is a personal repo where I pretended to put in practice many concepts, frameworks and technologies that I've been interested to play with for a long time.

Some of those are:
* Java 10 new characteristics. Including Java 9 modules.
* Websocket clients in JAVA
* Docker
* Amazon fun staff, like ECS, Cloudformation, DynamoDB, SNS, etc
* Sonar Online service
* Build a CICD pipeline in the cloud


##### sonar analysis
https://sonarcloud.io/organizations/red-wine/projects

To run static analysis in Sonar OnLine execute:
./gradlew sonarqube \
  -Dsonar.organization=red-wine \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.login={key}