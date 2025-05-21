# Charity Platform

A comprehensive platform for managing donations to disaster areas with real-time tracking and AI-based damage assessment.

## Project Structure

- **microservices/** - Backend services built with Spring Boot
  - account-service - Manages donor and charity accounts, balances, and authentication
  - donation-service - Accepts donation requests, validates funds, triggers transactions
  - transaction-service - Coordinates distributed transactions, handles 2-phase commit logic
  - notification-service - Sends real-time updates to donors

- **dashboard-frontend/** - Web-based dashboard built with React
  - Visualizes donations, real-time transaction status, heatmaps, and audit logs
  - Connects with notification-service for live updates

- **ai-damage-detector/** - Python service for damage detection in drone images
  - Uses ML/Computer Vision models to identify high damage zones
  - Helps with dynamic donation routing

- **infra/** - Infrastructure automation and containerization
  - Docker Compose configuration for local development
  - Deployment automation for cloud environments

## Getting Started

[TBD]

## Development

[TBD]

## Deployment

[TBD]
