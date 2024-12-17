# Task Manager API

A Quarkus-based REST API for task management.

## Prerequisites

- JDK 21
- Docker and Docker Compose
- Maven

## Tech Stack

- Quarkus 3.17.4
- MongoDB with Panache
- Redis Cache
- Kotlin
- Docker

## Getting Started

### 1. Start Required Services

The project uses MongoDB and Redis. Start them using Docker Compose:

```bash
docker-compose up -d
```

This will start:

- MongoDB on port 27017
- Redis on port 6379

### 2. Running the Application

#### Development Mode

```bash
./mvnw quarkus:dev
```

This enables:

- Live coding
- Dev UI at http://localhost:8080/q/dev/
- Automatic restart on code changes

#### Production Mode

```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

### 3. Building Docker Image

```bash
# JVM Mode
./mvnw package
docker build -f src/main/docker/Dockerfile.jvm -t taskmanager:latest .

# Native Mode (requires GraalVM)
./mvnw package -Dnative
docker build -f src/main/docker/Dockerfile.native -t taskmanager:native .
```

## CI/CD Pipeline

The project includes a GitHub Actions workflow (`ci.yml`) that:

1. Triggers on:

   - Push to main branch
   - Pull requests to main branch

2. Pipeline Steps:
   - Checks out code
   - Sets up JDK 21 (Temurin distribution)
   - Caches Maven dependencies
   - Builds and tests the application
   - Builds Docker image

## Caching Implementation (Incomplete)

The application implements Redis caching using Quarkus' cache extension:

- Task retrieval is cached using `@CacheResult(cacheName = "task")`
- Cache invalidation occurs on updates/deletes using `@CacheInvalidate(cacheName = "task")`
- Cache configuration is defined in `application.properties`

TODO:

- Cache is failing when fetching a non-existent task because quarkus.redis.cache doesn't cache null values when using `@CacheResult` annotation.
- Fix would be to throw a custom `NotFoundException` when a task is not found and handle it properly in `TaskController.tk`. Or implement custom caching logic with a redis client.

## API Endpoints

- `GET /tasks` - List all tasks
- `GET /tasks/{id}` - Get a specific task
- `POST /tasks` - Create a new task
- `PUT /tasks/{id}` - Update a task
- `DELETE /tasks/{id}` - Delete a task

## Configuration

Key configurations in `application.properties`:

- MongoDB connection settings
- Redis cache configuration
- CORS settings
- Dev Services configuration for local development

## Development Notes

- Dev Services automatically start MongoDB and Redis in development mode
- The application uses Quarkus' dev mode for hot reload capabilities
- CORS is enabled and configured to accept requests from any origin
