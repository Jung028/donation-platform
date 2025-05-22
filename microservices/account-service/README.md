# Account Service

## Overview
The Account Service is a core microservice within the charity platform that manages user accounts, authentication, roles, and financial account information. It provides essential user management functionality and maintains financial account data used for donations and transactions. This service acts as the central user identity and account management system for the entire platform.

## Features
- User registration and authentication
- Role-based access control (RBAC)
- Financial account management
- Account balance tracking
- User profile management
- Funds availability validation
- Transaction history tracking

## Architecture
This microservice is built using Spring Boot and follows a layered architecture:

1. **Controller Layer**: Handles HTTP requests and responses, including authentication endpoints
2. **Service Layer**: Contains business logic for user and account management
3. **Repository Layer**: Manages data persistence for users, roles, and accounts
4. **Security Layer**: Implements authentication and authorization
5. **Model Layer**: Defines the domain entities and DTOs

## Project Structure
