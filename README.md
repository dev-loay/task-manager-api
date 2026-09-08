# Task Manager API

A learning project built with Spring Boot to practice CRUD operations, JPA relationships, and layered architecture. This is the first project in a structured backend learning path — not intended as a polished portfolio piece.

## Tech Stack

- Java 21, Spring Boot 4.1.1, Spring Data JPA, PostgreSQL, Lombok

## Features

- CRUD for Users and Tasks
- One-to-Many relationship (User → Tasks)
- DTOs, centralized exception handling, and validation
- Ownership check (a user can only access their own tasks)

## Endpoints

**Users:** `/api/users` (POST, GET, PUT, DELETE)

**Tasks:** `/api/user/{userId}/task` (POST, GET, PUT, PATCH `/status`, DELETE)

## Run Locally

```bash
git clone https://github.com/your-username/task-manager-api.git
cd task-manager-api
```

Update `application.properties` with your PostgreSQL credentials, then:

```bash
./mvnw spring-boot:run
```

## Note

No authentication yet — `userId` is passed manually in the URL for now. Security is introduced in a later project in this learning path.