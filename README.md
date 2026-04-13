# Building Climate Control

A full-stack building climate control system built as a monorepo with:

- backend : Spring Boot + JPA + PostgreSQL
- frontend : Next.js + TypeScript + Ant Design

The application manages a single building containing apartments and common rooms, allows the requested building temperature to be updated, and recalculates each room's operating mode (`HEATING`, `COOLING`, `IDLE`) based on current temperature and threshold rules.

## Features

- View building details, apartments, and common rooms
- Add, edit, and delete apartments
- Add, edit, and delete common rooms
- Update the requested building temperature
- Scheduled backend recalculation of room temperatures
- "Close enough" threshold support so rooms can remain `IDLE`
- Dockerized backend, frontend, and PostgreSQL
- Backend unit tests

## Monorepo Structure

```text
DaikinAssesment/
├── backend/
│   └── building-climate-control/
├── frontend/
│   └── building-climate-control/
├── docker-compose.yml
└── README.md
```

## Tech Stack

### Backend

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- JaCoCo

### Frontend

- Next.js
- React
- TypeScript
- Ant Design

## Domain Model

### Building

- has many apartments
- has many common rooms
- has a requested building temperature

### Apartment

- unique room id
- unit Number
- owner name
- current temperature
- acMode: [HEATING , COOLING,IDLE]

### Common Room

- unique room id
- room type: [GYM, LIBRARY, LAUNDRY]
- current temperature
- acMode: [HEATING , COOLING,IDLE]

## Business Rules

- Initial room temperature is random between 10 and 40
- The building has a requested temperature
- A room becomes:
  - HEATING if below requested temperature outside threshold
  - COOLING if above requested temperature outside threshold
  - IDLE if within the configured threshold
- A scheduled task slowly adjusts room temperatures toward the target temperature

## Assumptions

- The application manages a single building
- A single building is always there

## Local Setup

### Prerequisites

- Java 17+
- Node.js 20+
- PostgreSQL 15+
- Maven Wrapper (`./mvnw` is already included)

### 1. Start PostgreSQL

Create a database named:
building_climate_control

Use credentials:
username: admin
password: admin

### 2. Run Backend

cd backend/building-climate-control
./mvnw spring-boot:run

Backend runs on:

http://localhost:8080

### 3. Run Frontend

cd frontend/building-climate-control
npm install
npm run dev

Frontend runs on:

http://localhost:3000

## Running Tests

### Backend Tests

cd backend/building-climate-control
./mvnw test

Coverage report:
open target/site/jacoco/index.html

## Docker

The project includes Docker support for:

- PostgreSQL
- Spring Boot backend
- Next.js frontend
- backend test runner

### Build and Run Full Stack
docker compose up --build

Application URLs:

- Frontend: `http://localhost:3000`
- Backend: `http://localhost:8080`

### Run Backend Tests in Docker

docker compose run --rm backend-tests

### Stop Containers

docker compose down

### Stop Containers and Remove Volumes

docker compose down -v

## API Summary

### Building

- `GET /buildings`
- `PUT /buildings/temperature`

### Apartments

- `POST /apartments`
- `DELETE /apartments/{id}`

### Common Rooms

- `GET /commonRooms`
- `GET /commonRooms/{id}`
- `POST /commonRooms`
- `DELETE /commonRooms/{id}`

### Rooms

- `PUT /rooms/{id}`

## What has been done
- The backend uses scheduled recalculation to simulate real temperature drift over time
- Configurable parameters for schedule time, threshold and coolingRate
- The frontend supports manual refresh and can be extended with polling/auto-refresh
- Backend tests cover business logic and services flows

## Possible Future Improvements

- Add stricter request validation annotations
- Add frontend page/integration tests
- Add CI workflow for lint, test, and Docker validation
- Improve live update behavior with polling or WebSocket/SSE
- Authentication
