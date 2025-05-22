# Donation Service

## Overview
The Donation Service is a microservice within the charity platform that manages donation transactions. It's responsible for accepting donation requests, validating funds from donors, and triggering financial transactions. This service is a critical component of the platform, facilitating the flow of funds from donors to charitable campaigns.

## Features
- Accepts and processes donation requests
- Validates fund availability from donor accounts
- Triggers financial transactions
- Tracks donation status throughout the process
- Provides APIs for creating and retrieving donation information

## Architecture
This microservice is built using Spring Boot and follows a layered architecture:

1. **Controller Layer**: Handles HTTP requests and responses
2. **Service Layer**: Contains business logic for donation processing
3. **Repository Layer**: Manages data persistence
4. **Client Layer**: Communicates with other microservices using Feign clients
5. **Model Layer**: Defines the domain entities and DTOs

## Project Structure
