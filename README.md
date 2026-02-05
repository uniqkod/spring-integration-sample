# Spring Integration REST Endpoint Sample

This project demonstrates how to create REST endpoints using Spring Integration's HTTP inbound gateway.

## What's Included

1. **Main Application** (`ApiGatewayApplication.java`)
   - Spring Boot application with `@EnableIntegration` annotation

2. **Integration Configuration** (`IntegrationConfig.java`)
   - Two REST endpoints created using Spring Integration DSL:
     - `GET /api/hello` - Returns "Hello World"
     - `POST /api/echo` - Echoes back the request payload

3. **Application Configuration** (`application.yml`)
   - Server runs on port 8080
   - Debug logging enabled for Spring Integration

## How to Run

1. Build the project:
   ```bash
   mvn clean install
   ```

2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

3. Test the endpoints:

   **GET endpoint:**
   ```bash
   curl http://localhost:8080/api/hello
   ```
   Response: `Hello World`

   **POST endpoint:**
   ```bash
   curl -X POST http://localhost:8080/api/echo \
     -H "Content-Type: text/plain" \
     -d "Test message"
   ```
   Response: `Echo: Test message`

## Customizing the URI

To change the endpoint URI, modify the path in `IntegrationConfig.java`:

```java
@Bean
public IntegrationFlow httpGetFlow() {
    return IntegrationFlow
        .from(Http.inboundGateway("/your/custom/path")  // Change this
            .requestMapping(m -> m.methods(HttpMethod.GET))
            .requestPayloadType(String.class))
        .<String, String>transform(payload -> "Hello World")
        .get();
}
```

## How It Works

Spring Integration uses the **Enterprise Integration Patterns** approach:

1. **Inbound HTTP Gateway** - Receives HTTP requests and converts them to Spring Integration messages
2. **Transformer** - Processes the message (in this case, returns "Hello World")
3. **Response** - The transformed message is automatically sent back as HTTP response

This is a declarative, flow-based approach to building REST endpoints, which is particularly useful for:
- Message routing and transformation
- Integration with other systems
- Complex message processing pipelines
- Protocol bridging (HTTP to JMS, etc.)

## Dependencies Added

- `spring-integration-http` - HTTP support for Spring Integration
- `spring-boot-starter-web` - Embedded Tomcat and Spring MVC (required for HTTP endpoints)

## Next Steps

You can extend this sample by:
- Adding message channels for async processing
- Integrating with databases, message queues, or external APIs
- Adding filters, routers, and splitters for complex message flows
- Implementing error handling and retry logic
