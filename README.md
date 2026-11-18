# CityExplorer Backend

CityExplorer is a Spring Boot REST API that helps users discover places, explore attractions, save favorites, write reviews, find accommodations, and plan personalized travel itineraries.

## Tech Stack

- Java
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA / Hibernate
- PostgreSQL
- Flyway Database Migration
- Swagger / OpenAPI
- Maven

## Features

- User registration and login with JWT authentication
- Browse places and their attractions
- Find nearby attractions using geographic coordinates
- Add and remove favorite places
- Create, update, and delete attraction reviews
- Browse and manage accommodations
- Create and manage personalized itineraries
- Automatically generate itinerary days
- Add, remove, and reorder attraction stops
- User-specific data access and authorization

## Prerequisites

Make sure you have installed:

- Java
- Maven
- PostgreSQL

## Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE cityexplorer_db;
```

The application uses environment variables for database configuration.

| Variable | Default value |
|---|---|
| DB_URL | jdbc:postgresql://localhost:5432/cityexplorer_db |
| DB_USERNAME | postgres |
| DB_PASSWORD | postgres |

Update these values according to your local PostgreSQL configuration.

## Environment Variables

Configure the following environment variables before starting the application:

```properties
DB_URL=jdbc:postgresql://localhost:5432/cityexplorer_db
DB_USERNAME=postgres
DB_PASSWORD=your_database_password
JWT_SECRET=your_jwt_secret
```

**Important:** Keep your actual database password and JWT secret private. Never commit secrets to GitHub.

## Database Migrations

CityExplorer uses Flyway to manage database schema changes.

Flyway migrations run automatically when the application starts.

Hibernate is configured with:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

This means Hibernate validates the database schema rather than automatically modifying it.

## Running the Application

Clone the repository:

```bash
git clone https://github.com/DhirajThaku/city-explorer
```

Navigate to the project directory:

```bash
cd CityExplorer
```

Build the application:

```bash
mvn clean install -DskipTests
```

Run the application:

```bash
mvn spring-boot:run
```

The backend starts at:

```text
http://localhost:8080
```

## API Documentation

CityExplorer uses Swagger UI for interactive API documentation.

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

## Authentication

CityExplorer uses JWT-based authentication.

1. Register a new user using `/api/auth/register`.
2. Login using `/api/auth/login`.
3. Obtain the JWT token from the login response.
4. Use the token to access protected endpoints.

Include the token in the request header:

```text
Authorization: Bearer <your-jwt-token>
```

## Health Check

The application exposes a health endpoint:

```text
http://localhost:8080/actuator/health
```

## Project Status

**Phase 1 – Backend Development:** Core backend features and API documentation completed.

**Phase 2 – Frontend Development:** Planned using Angular.

**Future Enhancement:** AI-powered personalized trip planning using LLM integration.
