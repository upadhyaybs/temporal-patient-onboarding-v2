# Temporal Demo: New Patient Onboarding Workflow

This is Temporal Workflow Demo for new Patient Onboarding. Workflow exposes REST endpoint for consumer to call.

## Framework
 - Spring Boot
 - Temporal Java SDK
 - Java 11
 - Maven

## Build the project

Open the project in IntelliJ or Eclipse which will automatically build it, or in project directory run the following command

```
mvn clean build
```

## Pre-requisite

You need to start Temporal Server before starting workflow. Go to docker-compose folder and run following command

```
docker-compose up
```

##### Note:

````
Docker must be installed and running on your local machine before starting this workflow
````

## Run the project

First make sure there are no compilation error. You can run this in your favorite IDE or use the following command 

```
mvn spring-boot:run
```

## Test API

You can use any rest client tool like Postman to test APIs. You can change port # in application.properties file.

Swagger URL :

```
http://localhost:8086/swagger-ui/index.html
```

API Endpoint : 

```
http://localhost:8086/onboard
```

## Temporal Documentation

https://docs.temporal.io


