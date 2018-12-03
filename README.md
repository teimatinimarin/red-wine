# red-wine
This is a personal repo where I pretended to put in practice many concepts, frameworks and technologies that I've been interested to play with for a long time.

Some of those are:
* Java 9 to 11 new features, including:
  * Module System (JSR 376)
  * Local-Variable Type Inference (JEP-323)
  * Native WebSocket support (JEP-321)
* Websocket clients in JAVA
* Docker
* Amazon fun staff, like ECS, Cloudformation, DynamoDB, SNS, etc
* Sonar Online service
* Build a CICD pipeline in the cloud


##### sonar analysis
https://sonarcloud.io/organizations/red-wine/projects

To run static analysis in Sonar OnLine execute:
mvn sonar:sonar \
  -Dsonar.organization=red-wine \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.login={key}

Gradle is not in use in this project be the moment...
./gradlew sonarqube \
  -Dsonar.organization=red-wine \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.login={key}
  
  
#### libraries used in this project
* [Tyrus] (https://tyrus-project.github.io/)
* [Log4J2] (https://logging.apache.org/log4j/2.x/)
* [Jackson] (https://github.com/FasterXML/jackson)
* [NewHttp] Need to specify --add-modules jdk.incubator.httpclient 

#### Maven plugins used in this project
* org.sonarqube to run Static Analysis