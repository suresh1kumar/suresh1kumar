# Service Module

<!-- ABOUT THE MODULE -->

## About The Service Module

Use this for defining micro-service orchestration and implementation of business requirements.

## Test
Run test with jacoco
./gradlew clean test jacocoTestReport

## Build
./gradlew build

## Run 
In folder /service/build/libs
java -jar service-0.0.1-SNAPSHOT.jar

Check on
http://localhost:9081/actuator/info