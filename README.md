# AI-Powered Disaster Relief Platform

A microservices-based platform that uses AI to detect disaster zones, assess damage, and match donations to areas of greatest need.

## Project Structure

```
donation-platform/
├── microservices/
│   ├── account-service/         # User account management
│   ├── donation-service/        # Donation processing
│   ├── transaction-service/     # Transaction handling
│   └── notification-service/    # Real-time notifications
├── dashboard-frontend/          # React-based dashboard
│   ├── public/
│   ├── src/
│   └── package.json
├── ai-damage-detector/          # Python ML service
│   ├── app.py
│   └── model/
└── infra/                      # Infrastructure configuration
    └── docker-compose.yml
```

## Services Overview

- **Account Service**: Manages user accounts, authentication, and profiles
- **Donation Service**: Handles donation processing and tracking
- **Transaction Service**: Manages financial transactions and audit logs
- **Notification Service**: Real-time alerts and updates
- **AI Damage Detector**: Uses ML to assess disaster zones
- **Frontend Dashboard**: Interactive web interface with maps and analytics

## Getting Started

1. Clone the repository
2. Set up environment variables (see `.env.example`)
3. Build and run services using Docker Compose:
   ```bash
   cd infra
   docker-compose up
   ```

## Prerequisites

- Docker and Docker Compose
- Azure Storage account (for production)
- Python 3.8+ (for AI service)
- Node.js 16+ (for frontend)

## License

This project is licensed under the MIT License - see the LICENSE file for details.
