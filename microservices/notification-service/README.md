# Notification Service

## Overview
The Notification Service is a microservice responsible for managing and delivering notifications to users across the charity platform. It supports multiple notification channels including email, SMS, and real-time in-app notifications via WebSockets. This service is event-driven and reacts to actions performed in other microservices to keep users informed about relevant activities.

## Features
- Multi-channel notification delivery (Email, SMS, In-app)
- Real-time notifications using WebSocket
- Notification templates for consistent messaging
- Notification preferences management
- Notification history tracking
- Bulk notification capabilities
- Notification read/unread status management

## Architecture
This microservice is built using Spring Boot and follows a layered architecture:

1. **Controller Layer**: Handles HTTP requests and WebSocket connections
2. **Service Layer**: Contains business logic for notification processing and delivery
3. **Model Layer**: Defines the domain entities and DTOs
4. **Integration Layer**: Communicates with external services (email, SMS providers)

## Project Structure
