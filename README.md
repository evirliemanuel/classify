## Overview

Classify is a simple REST API project. It was build for fun and an excuse to write in Kotlin. You can also check my other projects that consumed this api
[here](https://github.com/erafaelmanuel/classify-android) and [here](https://github.com/erafaelmanuel/classify-frontend)

## Project Setup

You run it using terminal:
```
mvn spring-boot:run
```

Edit the datasource configuration in application.yml
```
datasource:
    url: <url>
    username: <your-username>
    password: <your-password>
    driver-class-name: <driver-class-name>
```

## Authentication

Create your user acount by sending a POST request to [http://localhost:5002/auth/oauth/token](http:localhost:8085/api/developer/creatToken) with your username and password inside the body.
On success, user information and API token will be returned:
```
{
  "id": 1,
  "username": "verliemanuel",
  "password": "123"
  "role": "visitor",
  "token": "cb810da7d03b85335ea18babf3536fbf2a31ccdf"
}
```
All subsequent API requests must include this token in the HTTP header for user identification.
Header key will be `Authorization` with value of 'Token' followed by a single space and then token string:
```
Authorization: Token 753da61b4c39bd195782710c82fe3c3b1e7f7428
```

## Contributing
To contribute, please send us a [pull request](https://github.com/anverliedoit/classify/pulls) from your fork of this repository.
