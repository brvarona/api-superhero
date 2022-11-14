# REST API with Spring Boot 2 and Java 11

![](https://img.shields.io/badge/Java11-orange.svg)
![](https://img.shields.io/badge/Spring%20Boot-2.7.5-green.svg)

This is a sample Java / Maven / Spring Boot (version 2.7.5) application that can be used as a starter for creating an API Rest with elementary CRUD operations.

## Content of repository
- Connection to a database in `H2` stored in memory
- `maven` project with `SpringBoot`
- Uses `JPA` for database maintenance
- Security authentication with `Basic Auth`
- Documentation with `Swagger` offering a user interface with `Swagger-ui`
- Tests with `Junit` and `MockMVC`
- `TrackExecutionTime` annotation is created to control the execution time of some services
- Using a `HandlerException` to handle and customize exceptions
- Some requests are cached with `Spring Cache`

## How to run:
Compile and prepare jar:
```
mvn clean install
```
Run application:
```
mvn spring-boot:run
```
Base path API Rest: http://localhost:8080


## REST Services ##
- Without authentication:
  - GET /api/v1/superheros 
  - GET /api/v1/superheros/{id} 
  - DELETE /api/v1/superheros/cache 
  
- With authentication (BASIC AUTH, user:admin/password:admin):
  - PUT /api/v1/superheros/{id} 
  - PUT /api/v1/superheros/{id} 

    
## Docker ##
Command to build the container:
```
docker build -t superhero .
```
Command to execute the container:
```
run -p 8080:8080 superhero
```
## Documentación API ##
http://localhost:8080/v2/api-docs

## Swagger UI ##
http://localhost:8080/swagger-ui.html