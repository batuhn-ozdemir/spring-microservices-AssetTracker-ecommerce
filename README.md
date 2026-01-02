Spring Boot Microservices AssetTracker Project

Hi! This is my microservices backend project. It's still a work in progress, but the core features are up and running.

✅ What's Done So Far
Identity Service: User registration and login with JWT.
Order Service: Handles orders and communicates with the inventory.
Inventory Service: Manages stock levels and reduces quantity on purchase.
Notification Service: Listens to RabbitMQ to send order notifications.
Service Discovery: Eureka server is set up for service registration.
API Gateway: All requests flow through a single entry point.

🚧 Current Status
The basic order-stock-notification flow is working. I've tested some advanced features like Circuit Breakers, but I'm currently refining the implementation to make it more stable.

📅 To-Do List
Add Resilience4j for better fault tolerance.
Implement Distributed Tracing (Zipkin/Micrometer) to track logs.
Add Docker support for easy deployment.
Refactor exception handling.
