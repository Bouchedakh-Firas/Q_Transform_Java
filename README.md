# Java 8 Web Application

A simple Java 8 web application built with Spring Boot.

## Project Overview

This project demonstrates a basic Java 8 web application with the following features:
- Spring Boot framework for web development
- MVC architecture (Models, Views, Controllers)
- RESTful API endpoints
- Thymeleaf templates for server-side rendering
- Basic landing page with API integration

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── app/
│   │               ├── controllers/    # Web and API controllers
│   │               ├── models/         # Data models
│   │               ├── services/       # Service classes
│   │               └── Application.java # Main application class
│   └── resources/
│       ├── templates/                  # Thymeleaf templates
│       └── application.properties      # Application configuration
└── test/
    └── java/                           # Test classes
```

## Prerequisites

- Java 8 JDK
- Maven 3.6+ or compatible build tool

## Building the Project

To build the project, run:

```bash
mvn clean package
```

## Running the Application

To run the application, use:

```bash
mvn spring-boot:run
```

Or after building:

```bash
java -jar target/java8-web-app-0.0.1-SNAPSHOT.jar
```

The application will be available at http://localhost:8080

## API Endpoints

- `GET /api/welcome` - Returns a welcome message with timestamp
- `POST /api/signature` - Generates an email signature based on provided parameters
  - Parameters:
    - `firstName` - First name of the person
    - `lastName` - Last name of the person
    - `jobTitle` - Job title of the person
- `GET /api/joke` - Returns a random joke

## Testing

To run the tests:

```bash
mvn test
```
