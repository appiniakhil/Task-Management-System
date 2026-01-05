# Task Management System API

A simple REST API for a Task Management System built with Spring Boot, PostgreSQL, and Flyway.

## Tech Stack
- **Java 21**
- **Spring Boot 3.4.1**
- **PostgreSQL 18.0**
- **Flyway** (Database Migrations)
- **Spring Security** (API Key Authentication)
- **Lombok**

## Prerequisites
- Java 21 SDK
- PostgreSQL installed and running
- Maven (wrapper included)

## Setup Instructions

1.  **Clone the repository**
    ```bash
    git clone https://github.com/appiniakhil/Task-Management-System.git
    cd Task_Management_System
    ```

2.  **Configure Database**
    Update `src/main/resources/application.properties` if your credentials differ:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5434/task_management_system
    spring.datasource.username=postgres
    spring.datasource.password=0000
    ```
    *Ensure the database `task_management_system` exists.*

3.  **Run the Application**
    ```bash
    ./mvnw spring-boot:run
    ```
    The application will start on `http://localhost:8080`.

## Authentication

All API endpoints are secured using a static API Key.
You must include the following header in all requests:

`X-API-KEY: your-secret-api-key-12345`

## API Endpoints

### Users
- `POST /api/users` - Create a new user
- `GET /api/users` - List all users (supports pagination: `?page=0&size=10`)
- `GET /api/users/{id}` - Get a user by ID

### Tasks
- `POST /api/tasks` - Create a new task
- `GET /api/tasks` - List tasks (supports filtering by `status`, `priority` and pagination)
- `GET /api/tasks/{id}` - Get a task by ID
- `PUT /api/tasks/{id}` - Update a task
- `PATCH /api/tasks/{id}/status` - Update task status
- `DELETE /api/tasks/{id}` - Delete a task

## Database Migrations
Migrations are managed by Flyway and located in `src/main/resources/db/migration`.
- `V1__init_schema.sql`: Initial schema for Users and Tasks.
