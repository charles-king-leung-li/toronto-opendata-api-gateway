# Toronto Open Data API Gateway

> **Public-facing API Gateway for Toronto Open Data microservices**

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

This is the **Backend for Frontend (BFF)** layer that serves as the public-facing API Gateway for the Toronto Open Data platform. It handles all client requests, routes them to the Core Service, and manages response wrapping, CORS, and API key configuration.

## 🏗️ Architecture

```
┌─────────────────────┐
│  React Native App   │  Frontend (Mobile/Web)
│   (Port 3000/19006) │
└──────────┬──────────┘
           │ HTTPS/REST
┌──────────▼──────────────────────────────────┐
│      API Gateway (Port 8080)                │  ← This Service
│  • Request routing via Feign               │
│  • Response wrapping (ApiResponse)          │
│  • CORS handling                            │
│  • Google Maps API key management           │
│  • OpenAPI/Swagger documentation            │
└──────────┬──────────────────────────────────┘
           │ Feign Client (HTTP)
┌──────────▼──────────────────────────────────┐
│      Core Service (Port 8081)               │
│  • Business logic                           │
│  • Data processing                          │
│  • Database operations                      │
└─────────────────────────────────────────────┘
```

## 🚀 Quick Start

### Prerequisites
- **Java 17+**
- **Maven 3.9.11+**
- **Core Service** running on port 8081
- Port 8080 available

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/charles-king-leung-li/toronto-opendata-api-gateway.git
cd toronto-opendata-api-gateway
```

2. **Configure environment** (optional)
```bash
# Create .env file for API keys
echo "GOOGLE_MAPS_API_KEY=your_key_here" > .env
```

3. **Start the Gateway**
```bash
# Using Maven Wrapper (recommended)
./mvnw spring-boot:run

# Or with installed Maven
mvn spring-boot:run
```

The API Gateway will start on **http://localhost:8080**

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 17 | Programming language |
| Spring Boot | 3.5.7 | Application framework |
| Spring Cloud OpenFeign | 4.x | Inter-service communication |
| SpringDoc OpenAPI | 2.2.0 | API documentation |
| Lombok | Latest | Boilerplate reduction |

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

## 📡 API Endpoints

All endpoints are prefixed with `/api` and return responses wrapped in `ApiResponse<T>`.

### Cultural Hotspots
```http
GET /api/cultural-hotspots              # Get all cultural hotspots
GET /api/cultural-hotspots/{id}         # Get hotspot by ID
GET /api/cultural-hotspots/search?name={query}  # Search by name
```

### Map & GeoJSON
```http
GET /api/map/points                     # Get all map points
GET /api/map/geojson                    # Get GeoJSON format
GET /api/map/bounds?minLat={lat}&minLon={lon}&maxLat={lat}&maxLon={lon}
GET /api/map/nearby?lat={lat}&lon={lon}&radius={km}
```

### Configuration
```http
GET /api/config/maps-key                # Get Google Maps API key (for frontend)
```

### API Documentation
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **OpenAPI YAML**: http://localhost:8080/v3/api-docs.yaml

### Example Response Format
```json
{
  "data": [ /* your data here */ ],
  "metadata": {
    "totalCount": 500,
    "pageSize": 20,
    "pageNumber": 1,
    "totalPages": 25
  },
  "message": "Success",
  "status": "success"
}
```

## ⚙️ Configuration

### Environment Variables

Create a `.env` file (gitignored):
```bash
GOOGLE_MAPS_API_KEY=your_google_maps_api_key_here
CORE_SERVICE_URL=http://localhost:8081
```

### application.properties
```properties
# Server Configuration
server.port=8080

# Core Service Connection (Feign Client)
core.service.url=${CORE_SERVICE_URL:http://localhost:8081}

# Google Maps API Key
google.maps.api.key=${GOOGLE_MAPS_API_KEY:}

# CORS Configuration
cors.allowed.origins=http://localhost:3000,http://localhost:19006

# OpenAPI Configuration
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
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

## 🔐 CORS Configuration

Pre-configured to allow requests from:
- **http://localhost:3000** - React web development
- **http://localhost:19006** - React Native/Expo development

To add production origins, update `application.properties`:
```properties
cors.allowed.origins=https://yourdomain.com,https://www.yourdomain.com
```

## 🧪 Testing

### Run Tests
```bash
./mvnw test
```

### Build for Production
```bash
./mvnw clean package
```

JAR file will be in `target/toronto-opendata-api-gateway-0.0.1-SNAPSHOT.jar`

### Manual API Testing

Using `curl`:
```bash
# Get all cultural hotspots
curl http://localhost:8080/api/cultural-hotspots

# Get nearby locations
curl "http://localhost:8080/api/map/nearby?lat=43.65&lon=-79.38&radius=5"

# Get Google Maps API key
curl http://localhost:8080/api/config/maps-key
```

Using Swagger UI:
1. Open http://localhost:8080/swagger-ui.html
2. Try out endpoints interactively

## 📦 Deployment

### Running the JAR
```bash
java -jar target/toronto-opendata-api-gateway-0.0.1-SNAPSHOT.jar
```

### Docker (Future)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

## 🛣️ Roadmap

- [ ] Add authentication/authorization (JWT)
- [ ] Implement request validation
- [ ] Add caching layer (Redis)
- [ ] Implement rate limiting
- [ ] Add circuit breaker pattern (Resilience4j)
- [ ] Containerize with Docker
- [ ] Add health checks and monitoring
- [ ] Implement API versioning

## 🔗 Related Repositories

- **[Core Service](https://github.com/charles-king-leung-li/toronto-opendata-core-service)** - Business logic and data processing microservice
- **[React Native Frontend](https://github.com/charles-king-leung-li/TorontoOpenDataReactFE)** - Mobile application

## 📄 License

MIT License - see [LICENSE](LICENSE) file

## 👤 Author

**Charles King Leung Li**
- GitHub: [@charles-king-leung-li](https://github.com/charles-king-leung-li)

## 🙏 Acknowledgments

- Data from [City of Toronto Open Data](https://open.toronto.ca/)
- Built with [Spring Boot](https://spring.io/projects/spring-boot)
- API Gateway pattern inspired by microservices best practices
