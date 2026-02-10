# Github Integration

## Table of contents
* [1. Project Description](#1-project-description)
* [2. Technologies](#2-technologies)
* [3. Setup](#3-setup)
* [4. Application Configuration](#4-application-configuration)
* [5. API Spec](#5-api-documentation)
* [6. Unit tests](#6-unit-tests)
* [7. Postman Collection](#7-postman-collection)

## 1. Project Description

This project provides a REST API endpoint that allows clients to retrieve a subset 
of publicly available GitHub user data by supplying a GitHub username.

The service integrates with the official GitHub REST API, aggregates the required information, 
and returns a simplified and structured JSON response as shown in the example below:

```
{
    user_name: "octocat",
    display_name: "The Octocat",
    avatar: "https://avatars.githubusercontent.com/u/583231?v=4",
    geo_location: "San Francisco",
    email: null,
    url: "https://api.github.com/users/octocat",
    created_at: "Tue, 25 Jan 2011 18:44:36 GMT",
    repos: [{
                name: "boysenberry-repo-1",
                url: "https://api.github.com/repos/octocat/boysenberry-repo-1
            }, ...
    ]
}
```


## 2. Technologies

The technologies used to develop this system which it depends on were:

* Java 21
* SpringBoot 3.3.2
* Maven 3.6.3_1

## 3. Setup

The application can be started using Maven.

### 3.1. Launch an application on your local machine 

To start the Application go to the root directory of the project and run:

    mvn spring-boot:run

To stop the application from the terminal where it's running:

    ctrl+c

## 4. Application Configuration

The configuration file is available in the src/main/resources folder.
 
## 5. API Documentation

The API specification can be found at the following locations:

* Swagger API Spec: http://localhost:8081/swagger-ui/index.html

## 6. Unit Tests

To run the unit tests, execute the following command from the root directory of the project:

    mvn test

## 7. Postman Collection

A Postman collection is included in the src/main/resources directory to simplify manual testing of the API endpoints.

This collection contains pre-configured requests that allow you to quickly test the service without manually constructing HTTP requests.

How to use:

1. Open Postman 
2. Click Import
3. Select the collection file located in: 
   src/main/resources/github-integration.postman_collection.json
4. Run the requests and provide the desired GitHub username as a parameter

This allows you to easily validate the endpoint behavior and test different scenarios.
<img width="757" height="517" alt="image" src="https://github.com/user-attachments/assets/18d60106-f181-495b-9219-c8a60531526f" />

