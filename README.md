## Description

This is a sample Spring API DEMO.

## Library Used

Maven
Spring Boot
MyBatis
Flyway DB Migration

## API Endpoint Sample

User Resource:

1. `GET /api/users`
2. `GET /api/users/:id`
3. `POST /api/users` with `username` and `password`
4. `PUT /api/users/:id` with `username` and `password`
5. `DELETE /api/users/:id` will delete the Todos belongs to the User as well

Todo Resource:

1. `GET /api/users/:userId/todos`
2. `GET /api/users/:userId/todos/:id`

## TODOs

1. User Password Hash
2. User Authentication Checks for the Todo Resources
3. Request Param Validator
4. Request Exception Handlement
5. Testing should covers all Classes

## Run in Dev Env

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
