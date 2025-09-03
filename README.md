# Microservices
`It remains to write the tests`

This project demonstrates a microservices architecture using Spring Boot, Spring Cloud, and Docker. It consists of two main services (User Service and Company Service) that communicate with each other through REST APIs, with an API Gateway for routing and service discovery using Netflix Eureka.

## Project Structure

- `company-service`: Manages company information
- `user-service`: Manages user information
- `api-gateway`: Routes requests to appropriate services
- `eureka-server`: Service discovery server

## Prerequisites

- Java 21
- Maven
- Docker and Docker Compose

## Building the Project

1. Build all services:
   ```bash
   mvn clean install
   ```

## Running the Application

1. Start all services using Docker Compose:
   ```bash
   docker-compose up -d
   ```

2. The following services will be available:
   - API Gateway: http://localhost:8080
   - Eureka Server: http://localhost:8761
   - User Service: http://localhost:8082
   - Company Service: http://localhost:8081

## API Endpoints

### Company Service

- GET `/api/companies` - Get all companies
- GET `/api/companies/{id}` - Get company by ID
- POST `/api/companies` - Create a new company
- PUT `/api/companies/{id}` - Update a company
- DELETE `/api/companies/{id}` - Delete a company

### User Service

- GET `/api/users` - Get all users
- GET `/api/users/{id}` - Get user by ID
- GET `/api/users/company/{companyId}` - Get users by company ID
- POST `/api/users` - Create a new user
- PUT `/api/users/{id}` - Update a user
- DELETE `/api/users/{id}` - Delete a user

## Example Requests

### Create a Company
```bash
curl -X POST http://localhost:8080/api/companies \
  -H "Content-Type: application/json" \
  -d '{
    "companyName": "Example Company",
    "budget": 1000000
  }'
```

### Create a User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+1234567890",
    "companyId": 1
  }'
```
