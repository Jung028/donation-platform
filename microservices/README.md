# Account Service

## Overview

The Account Service is a Spring Boot microservice that manages donor and charity accounts, balances, and authentication for the Charity Platform. It provides RESTful APIs for account management and integrates with other microservices through Eureka service discovery.

## Key Features

- User Authentication and Authorization
- Account Management (Donors and Charities)
- Account Balance Management
- Role-based Access Control
- Integration with Eureka Service Discovery
- H2 Database for Local Development

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Authenticate user
- `GET /api/auth/me` - Get current user details

### Account Management
- `GET /api/accounts` - List all accounts
- `GET /api/accounts/{id}` - Get account details
- `POST /api/accounts` - Create new account
- `PUT /api/accounts/{id}` - Update account
- `DELETE /api/accounts/{id}` - Delete account

### Balance Management
- `GET /api/accounts/{id}/balance` - Get account balance
- `POST /api/accounts/{id}/deposit` - Add funds to account
- `POST /api/accounts/{id}/withdraw` - Withdraw funds

## Configuration

The service is configured to run on port 8082 and uses H2 database for local development. It requires Eureka service discovery running on port 8761.

### Application Properties
```properties
server.port=8082

spring.application.name=account-service
spring.datasource.url=jdbc:h2:mem:accountdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Eureka Client Configuration
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
```

## Building and Running

1. Build the service:
```bash
mvn clean install
```

2. Run the service:
```bash
java -jar target/account-service-1.0.0-SNAPSHOT.jar
```

3. Access the H2 Console at:
- http://localhost:8082/h2-console

4. Access the API documentation at:
- http://localhost:8082/swagger-ui.html

## Dependencies

- Spring Boot 3.2.2
- Spring Cloud Netflix Eureka
- Spring Security
- Spring Data JPA
- H2 Database
- Lombok
- Spring Boot Test

## Security

The service uses Spring Security for authentication and authorization. It supports:
- JWT Token Authentication
- Role-based Authorization
- Password Encryption
- CSRF Protection

## Development

For development, the service uses H2 database which is in-memory and resets on application restart. The H2 Console is available for database inspection during development.

## Contributing

Please see the main project's CONTRIBUTING.md for details on contributing to this service.
