![alt text](https://travis-ci.org/raviwu/spring-todo.svg?branch=master)

---

## Description

This is a sample Spring API DEMO.

## Library Used

Maven

Spring Boot

MyBatis

Flyway DB Migration

## API Endpoint DEMO

Authentication:

1. `POST /auth/register` with `username` and `password`
2. `POST /auth/login` with `username` and `password` to get JWT token

User Resource:

1. `GET /api/users`
2. `GET /api/users/:id`
3. `PUT /api/users/:id` with `username` and `password`
4. `DELETE /api/users/:id` will delete the Todos belongs to the User as well

Todo Resource:

1. `GET /api/users/:userId/todos`
2. `GET /api/users/:userId/todos/:id`
3. `POST /api/users/:userId/todos` with `description` to create new Todo under `userId`
4. `PUT /api/users/:userId/todos/:id` with `description` under `id`
5. `PUT /api/users/:userId/todos/:id/complete` to complete a todo
6. `DELETE /api/users/:userId/todos/:id` to delete a todo

## TODOs

1. JWT should be expired if user update password
2. Request Param Validator

## Run in Dev Env / Testing

```shell
# Unix bash, zsh or fish shell
mvn clean test -Dspring.profiles.active=test -B
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Windows PowerShell
mvn clean test -D'spring.profiles.active=test' -B
mvn spring-boot:run -D'spring-boot.run.profiles=dev'
```
