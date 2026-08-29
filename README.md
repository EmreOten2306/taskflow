# TaskFlow

TaskFlow is a backend REST API designed for project and task management.

The application allows users to create projects, manage tasks inside projects, assign tasks to users, add labels, and create comments on tasks.

The main goal of this project is to practice and demonstrate backend development concepts such as layered architecture, RESTful API design, DTO usage, entity relationships, validation, exception handling, pagination, and sorting.

## Features

### User Management

* Create, update, delete, and retrieve users
* Retrieve all users
* Retrieve tasks assigned to a specific user

### Project Management

* Create, update, delete, and retrieve projects
* Update project status
* Prevent duplicate project codes
* Prevent deleting a project when it still contains tasks

### Task Management

* Create tasks inside projects
* Update and delete tasks
* Retrieve tasks by project
* Assign tasks to users
* Update task status
* Add and remove labels from tasks
* Support task priority and status management
* Support pagination and sorting

### Label Management

* Create, update, delete, and retrieve labels
* Associate labels with tasks using a many-to-many relationship

### Comment Management

* Create comments for tasks
* Retrieve comments belonging to a task
* Update and delete comments
* Associate comments with both a task and an author

## Technology Stack

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* PostgreSQL
* Gradle
* Swagger / OpenAPI
* Postman
* Lombok
* Jakarta Validation

## Architecture

The project follows a layered architecture.

```text
Client
   ↓
Controller
   ↓
Request DTO
   ↓
Service
   ↓
Mapper
   ↓
Entity
   ↓
Repository
   ↓
Spring Data JPA / Hibernate
   ↓
PostgreSQL
```

The response flow works in the opposite direction:

```text
PostgreSQL
   ↓
Hibernate / JPA
   ↓
Repository
   ↓
Service
   ↓
Mapper
   ↓
Response DTO
   ↓
Controller
   ↓
Client
```

The Controller layer handles incoming HTTP requests and sends them to the Service layer.

The Service layer contains the business logic of the application.

DTOs are used to control the data received from and returned to the client.

Mappers are responsible for converting between DTOs and entities.

Repositories handle database operations through Spring Data JPA.

Hibernate translates persistence operations into SQL queries and communicates with PostgreSQL.

## Entity Relationships

The application contains the following main entities:

* AppUser
* Project
* Task
* Label
* Comment

Main relationships include:

```text
AppUser
 ├── owns Projects
 └── can be assigned to Tasks

Project
 └── contains Tasks

Task
 ├── belongs to a Project
 ├── can have an Assignee
 ├── has Labels
 └── has Comments

Comment
 ├── belongs to a Task
 └── has an Author
```

### Relationship Types

* Project → Task: One-to-Many
* Task → Label: Many-to-Many
* Task → Comment: One-to-Many
* Comment → AppUser: Many-to-One
* Comment → Task: Many-to-One

## DTO Usage

The application does not expose entities directly to the API.

Separate DTOs are used for:

* Create requests
* Update requests
* API responses

For example:

```text
CreateProjectRequest
        ↓
Project Entity
        ↓
ProjectResponse
```

This approach makes it possible to control which data is accepted from the client and which data is returned.

For example, the password hash stored in the user entity is not included in the API response.

## Validation

Jakarta Validation is used to validate incoming requests.

Examples include:


This helps prevent invalid data from reaching the business logic and database layers.

## Exception Handling

Custom exceptions are used for controlled error handling.

Examples:

* `ResourceNotFoundException`
* `DuplicateResourceException`

Errors are returned using a consistent API response structure.

Example:

```json
{
  "timestamp": "2026-08-27T21:40:11.303290Z",
  "status": 404,
  "error": "Not Found",
  "message": "Project not found with id: 999",
  "path": "/api/projects/999",
  "fieldErrors": []
}
```

Validation errors can also include detailed information about the invalid fields.

```json
{
  "field": "name",
  "message": "must not be blank"
}
```

## Pagination and Sorting

List endpoints support pagination and sorting using Spring's `Pageable`.

Example:

```text
/api/tasks?page=0&size=20&sort=createdAt,desc
```

Parameters:

* `page`: Specifies which page to retrieve
* `size`: Specifies how many records should be returned per page
* `sort`: Specifies the field and direction used for sorting

The `@ParameterObject` annotation is used so that Swagger can correctly expose the `Pageable` parameters as query parameters.

## API Documentation

The API can be tested using Swagger UI.

Swagger provides an interactive interface for testing the REST endpoints.

Postman can also be used to test the API endpoints and different request scenarios.

## Project Structure

The project is organized by feature.

```text
tech.ekya.taskflow
│
├── user
│   ├── dto
│   ├── AppUser
│   ├── AppUserController
│   ├── AppUserService
│   ├── AppUserRepository
│   └── AppUserMapper
│
├── project
│   ├── dto
│   ├── Project
│   ├── ProjectController
│   ├── ProjectService
│   ├── ProjectRepository
│   └── ProjectMapper
│
├── task
│   ├── dto
│   ├── Task
│   ├── TaskController
│   ├── TaskService
│   ├── TaskRepository
│   └── TaskMapper
│
├── label
│   ├── dto
│   ├── Label
│   ├── LabelController
│   ├── LabelService
│   ├── LabelRepository
│   └── LabelMapper
│
├── comment
│   ├── dto
│   ├── Comment
│   ├── CommentController
│   ├── CommentService
│   ├── CommentRepository
│   └── CommentMapper
│
├── exception
│   ├── ApiError
│   ├── FieldErrorDetail
│   ├── ResourceNotFoundException
│   └── DuplicateResourceException
│
└── common
    └── BaseEntity
```

The project follows a feature-based package structure, while each feature contains its own controller, service, repository, mapper, entity, and DTO classes.

## Current Status

The core backend functionality is implemented and the main endpoints are tested through Swagger and Postman.

Authentication and authorization are planned as the next stage of development. Security is currently disabled during the development and testing phase.

## Future Improvements

* JWT Authentication
* Role-based authorization
* Ownership-based access control
* Unit tests
* Integration tests
* Improved API documentation
* Additional filtering capabilities
* Production configuration

## Author

Developed by Emre Öten.
