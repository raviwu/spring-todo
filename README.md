## Description

This is a sample Spring API DEMO.

## Library Used

Maven

Spring Boot

MyBatis

Flyway DB Migration

## API Endpoint DEMO

User Resource:

1. `GET /api/users`
2. `GET /api/users/:id`
3. `POST /api/users` with `username` and `password`
4. `PUT /api/users/:id` with `username` and `password`
5. `DELETE /api/users/:id` will delete the Todos belongs to the User as well

Todo Resource:

1. `GET /api/users/:userId/todos`
2. `GET /api/users/:userId/todos/:id`
3. `POST /api/users/:userId/todos` with `description` to create new Todo under `userId`
4. `PUT /api/users/:userId/todos/:id` with `description` under `id`
5. `PUT /api/users/:userId/todos/:id/complete` to complete a todo
6. `DELETE /api/users/:userId/todos/:id` to delete a todo

## TODOs

1. User Password Hash
2. User Authentication Checks for the Todo Resources
3. Request Param Validator
4. Request Exception Handlement
5. Testing should covers main behaviors

## Run in Dev Env

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
