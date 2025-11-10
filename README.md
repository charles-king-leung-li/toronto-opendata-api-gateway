# Toronto Open Data - API Gateway

## Overview
This is the **Backend for Frontend (BFF)** layer of the Toronto Open Data microservices architecture. It serves as the API Gateway that handles all frontend requests and communicates with the Core Business Logic Service.

## Architecture
```
Frontend (React Native) → API Gateway (Port 8080) → Core Service (Port 8081)
```

## Tech Stack
- Java 17
- Spring Boot 3.5.7
- Spring Cloud OpenFeign (for inter-service communication)
- SpringDoc OpenAPI
- Lombok

## Running the Application

### Prerequisites
- Java 17
- Maven 3.9+
- Core Service running on port 8081

### Start the Server
```bash
./mvnw spring-boot:run
```

The API Gateway will start on **port 8080**.

## API Endpoints

### Cultural Hotspots
- `GET /api/cultural-hotspots` - Get all cultural hotspots
- `GET /api/cultural-hotspots/{id}` - Get hotspot by ID

### API Documentation
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Docs: http://localhost:8080/api-docs

## Configuration

### application.properties
```properties
server.port=8080
business.service.url=http://localhost:8081
```

## Project Structure
```
src/main/java/com/toronto/opendata/gateway/
├── ApiGatewayApplication.java          # Main application
├── client/
│   └── CoreServiceClient.java          # Feign client for Core Service
├── controller/
│   └── CulturalHotSpotController.java  # REST controllers
├── dto/
│   ├── ApiResponse.java                # Response wrapper
│   ├── CulturalHotSpotDTO.java        # Data transfer objects
│   └── MultiPointDTO.java
└── config/
    └── WebConfig.java                  # CORS configuration
```

## Responsibilities
- Accept HTTP requests from frontend
- Forward requests to Core Service via Feign Client
- Transform responses for frontend consumption
- Handle CORS
- API documentation via Swagger

## CORS Configuration
Allows requests from:
- http://localhost:3000 (React web)
- http://localhost:19006 (React Native/Expo)

## Next Steps
- Add authentication/authorization
- Implement request validation
- Add caching layer
- Implement rate limiting
- Add circuit breaker pattern

## Related Projects
- [Core Service](../toronto-opendata-core-service/) - Business logic and data persistence
- [Original Monolith](../toronto-opendata-api/) - Original monolithic application
