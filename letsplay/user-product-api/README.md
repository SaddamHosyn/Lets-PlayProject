# 🚀 User-Product API

A comprehensive Spring Boot REST API that provides secure user management and product catalog functionality with JWT authentication, role-based access control, and HTTPS security.

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
- [API Endpoints](#-api-endpoints)
- [Security Features](#-security-features)
- [Configuration](#-configuration)
- [Testing](#-testing)
- [Deployment](#-deployment)
- [Contributing](#-contributing)

## ✨ Features

### 🔐 **Authentication & Authorization**

- JWT-based stateless authentication
- Role-based access control (USER/ADMIN)
- BCrypt password hashing
- Custom security implementation on Spring Security
- 24-hour token expiration with configurable settings

### 👥 **User Management**

- User registration and authentication
- Profile management (view, update, delete)
- Email-based login system
- Admin-only user management operations

### 📦 **Product Management**

- CRUD operations for products
- Public product browsing (no authentication required)
- Owner-based product management
- Product search functionality
- Price and description validation

### 🛡️ **Security Features**

- HTTPS with SSL/TLS encryption
- Input validation with Bean Validation
- Sensitive data protection
- Global exception handling
- CORS support for frontend integration

## 🛠️ Tech Stack

### **Backend Framework**

- **Spring Boot 3.5.5** - Main application framework
- **Spring Security 6.x** - Security and authentication
- **Spring Data MongoDB** - Database integration
- **Spring Web** - REST API development

### **Database**

- **MongoDB** - NoSQL document database
- **Spring Data MongoDB** - Data access layer

### **Security & Authentication**

- **JWT (JSON Web Tokens)** - Stateless authentication
- **BCrypt** - Password hashing
- **HTTPS/SSL** - Transport layer security

### **Validation & Documentation**

- **Bean Validation (JSR-303)** - Input validation
- **Hibernate Validator** - Validation implementation

### **Build & Runtime**

- **Java 17** - Programming language
- **Maven** - Build automation
- **Embedded Tomcat** - Application server

## 🏗️ Architecture

### **Project Structure**

```
src/main/java/com/example/user_product_api/
├── config/
│   └── WebSecurityConfig.java          # Security configuration
├── controller/
│   ├── AuthController.java             # Authentication endpoints
│   ├── ProductController.java          # Product CRUD operations
│   └── UserController.java             # User management
├── dto/
│   ├── LoginRequest.java               # Login request model
│   ├── UserRegistrationRequest.java    # Registration model
│   └── UserResponse.java               # User response model
├── entity/
│   ├── Product.java                    # Product entity
│   └── User.java                       # User entity
├── exception/
│   ├── GlobalExceptionHandler.java     # Global error handling
│   ├── ResourceNotFoundException.java  # Custom exceptions
│   ├── UnauthorizedException.java
│   └── UserAlreadyExistsException.java
├── repository/
│   ├── ProductRepository.java          # Product data access
│   └── UserRepository.java             # User data access
├── security/
│   ├── AuthEntryPointJwt.java          # JWT entry point
│   ├── AuthTokenFilter.java            # JWT filter
│   ├── JwtUtils.java                   # JWT utilities
│   ├── UserDetailsImpl.java            # Custom user details
│   └── UserDetailsServiceImpl.java     # User details service
└── service/
    ├── ProductService.java             # Product business logic
    └── UserService.java                # User business logic
```

### **Security Architecture**

```
┌─────────────────────────────────────┐
│          HTTPS/SSL Layer            │
├─────────────────────────────────────┤
│         Spring Security             │
│     (Framework & Configuration)     │
├─────────────────────────────────────┤
│       Custom JWT Implementation     │
│   • Token Generation & Validation   │
│   • Custom Filters & Entry Points   │
│   • User Details Implementation     │
└─────────────────────────────────────┘
```

## 🚀 Getting Started

### **Prerequisites**

- Java 17 or higher
- Maven 3.6+
- MongoDB 4.4+
- Git

### **Installation**

1. **Clone the repository**

   ```bash
   git clone <https://github.com/SaddamHosyn/Lets-PlayProject.git>
   cd user-product-api
   ```

2. **Start MongoDB**

   ```bash
   # Using MongoDB service
   sudo systemctl start mongod

   # Or using Docker
   docker run -d -p 27017:27017 --name mongodb mongo:latest
   ```

3. **Configure application properties** (Optional)

   ```properties
   # src/main/resources/application.properties
   spring.data.mongodb.uri=mongodb://localhost:27017/userproductdb
   jwt.secret=your-custom-secret-key
   jwt.expiration=86400000
   ```

4. **Build and run the application**

   ```bash
   # Using Maven Wrapper (Recommended)
   ./mvnw clean spring-boot:run

   # Or using installed Maven
   mvn clean spring-boot:run
   ```

5. **Verify the application**
   - Application runs on: `https://localhost:8443`
   - Health check: `https://localhost:8443/actuator/health`

### **First Time Setup**

1. **Register an admin user** via API or directly in MongoDB
2. **Test authentication** by logging in
3. **Create sample products** to test the functionality

## 📚 API Endpoints

### **Authentication Endpoints**

| Method | Endpoint             | Description           | Access        |
| ------ | -------------------- | --------------------- | ------------- |
| `POST` | `/api/auth/register` | Register new user     | Public        |
| `POST` | `/api/auth/login`    | User login            | Public        |
| `GET`  | `/api/auth/me`       | Get current user info | Authenticated |
| `GET`  | `/api/auth/test`     | Test endpoint         | Public        |

### **User Management Endpoints**

| Method   | Endpoint                   | Description         | Access         |
| -------- | -------------------------- | ------------------- | -------------- |
| `GET`    | `/api/users`               | Get all users       | Admin only     |
| `GET`    | `/api/users/{id}`          | Get user by ID      | Admin or Owner |
| `PUT`    | `/api/users/{id}`          | Update user         | Admin or Owner |
| `DELETE` | `/api/users/{id}`          | Delete user         | Admin only     |
| `GET`    | `/api/users/{id}/products` | Get user's products | Admin or Owner |

### **Product Endpoints**

| Method   | Endpoint                           | Description          | Access         |
| -------- | ---------------------------------- | -------------------- | -------------- |
| `GET`    | `/api/products`                    | Get all products     | Public         |
| `POST`   | `/api/products`                    | Create product       | Authenticated  |
| `GET`    | `/api/products/{id}`               | Get product by ID    | Public         |
| `PUT`    | `/api/products/{id}`               | Update product       | Owner or Admin |
| `DELETE` | `/api/products/{id}`               | Delete product       | Owner or Admin |
| `GET`    | `/api/products/search?name={name}` | Search products      | Public         |
| `GET`    | `/api/products/user/{userId}`      | Get products by user | Admin or Owner |

### **Request/Response Examples**

#### **User Registration**

```bash
curl -X POST https://localhost:8443/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123"
  }'
```

#### **User Login**

```bash
curl -X POST https://localhost:8443/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

#### **Create Product (Authenticated)**

```bash
curl -X POST https://localhost:8443/api/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "name": "Laptop",
    "description": "High-performance laptop",
    "price": 999.99
  }'
```

## 🔒 Security Features

### **Implemented Security Measures**

1. **Password Security**

   - BCrypt hashing with salt
   - Minimum 6 characters requirement
   - No password exposure in API responses

2. **JWT Token Security**

   - HMAC SHA-256 signing
   - 24-hour expiration
   - Configurable secret key
   - Token validation on each request

3. **Input Validation**

   - Bean Validation annotations
   - Email format validation
   - Size and constraint validations
   - Global exception handling

4. **HTTPS/SSL**

   - TLS 1.2+ encryption
   - Self-signed certificate for development
   - HSTS headers for security
   - Secure cookie settings

5. **Access Control**
   - Role-based authorization
   - Method-level security
   - Owner-based resource access
   - Public/Private endpoint segregation

### **Security Annotations Used**

- `@EnableWebSecurity` - Enables Spring Security
- `@EnableMethodSecurity` - Enables method-level security
- `@PreAuthorize` - Pre-execution authorization
- `@PostAuthorize` - Post-execution authorization
- `@PermitAll` - Public access endpoints

## ⚙️ Configuration

### **Environment Variables**

```bash
# JWT Configuration
export JWT_SECRET=your-secret-key-here
export JWT_EXPIRATION=86400000

# Database Configuration
export MONGODB_URI=mongodb://localhost:27017/userproductdb

# Server Configuration
export SERVER_PORT=8443
export SSL_ENABLED=true
```

### **Application Properties**

```properties
# Database
spring.data.mongodb.uri=mongodb://localhost:27017/userproductdb
spring.data.mongodb.database=userproductdb

# Server & SSL
server.port=8443
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=changeit

# JWT
jwt.secret=${JWT_SECRET:default-secret}
jwt.expiration=${JWT_EXPIRATION:86400000}

# Logging
logging.level.com.example.userproductapi=DEBUG
```

### **MongoDB Setup**

```javascript
// Connect to MongoDB and create database
use userproductdb

// Create collections (optional - Spring Data will create them)
db.createCollection("users")
db.createCollection("products")

// Create indexes for better performance
db.users.createIndex({"email": 1}, {"unique": true})
db.products.createIndex({"name": 1})
db.products.createIndex({"userId": 1})
```

## 🧪 Testing

### **Manual Testing with cURL**

1. **Register a new user**
2. **Login to get JWT token**
3. **Create products using the token**
4. **Test public endpoints without authentication**
5. **Test admin operations**

### **Testing Tools**

- **Postman/Insomnia** - API testing
- **MongoDB Compass** - Database inspection
- **Browser Developer Tools** - HTTPS verification

### **Sample Test Workflow**

```bash
# 1. Register user
curl -X POST https://localhost:8443/api/auth/register -d '{"name":"Test","email":"test@test.com","password":"test123"}'

# 2. Login
TOKEN=$(curl -X POST https://localhost:8443/api/auth/login -d '{"email":"test@test.com","password":"test123"}' | jq -r '.token')

# 3. Create product
curl -X POST https://localhost:8443/api/products -H "Authorization: Bearer $TOKEN" -d '{"name":"Test Product","price":99.99}'

# 4. Get products (public)
curl https://localhost:8443/api/products
```

## 🚀 Deployment

### **Local Development**

```bash
./mvnw spring-boot:run
```

### **Production Build**

```bash
./mvnw clean package
java -jar target/user-product-api-0.0.1-SNAPSHOT.jar
```

### **Docker Deployment**

```dockerfile
FROM openjdk:17-jre-slim
COPY target/user-product-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8443
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### **Environment-Specific Configuration**

- **Development**: `application-dev.properties`
- **Production**: `application-prod.properties`
- **Testing**: `application-test.properties`

## 📋 API Response Formats

### **Success Response**

```json
{
  "id": "user123",
  "name": "John Doe",
  "email": "john@example.com",
  "role": "USER"
}
```

### **Error Response**

```json
{
  "error": "Validation Failed",
  "message": "Input validation error",
  "status": 400,
  "details": {
    "email": "Email should be valid",
    "password": "Password must be at least 6 characters"
  }
}
```

### **JWT Token Response**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "type": "Bearer",
  "id": "user123",
  "email": "john@example.com",
  "name": "John Doe",
  "roles": ["ROLE_USER"]
}
```

## 🔧 Troubleshooting

### **Common Issues**

1. **MongoDB Connection Failed**

   - Ensure MongoDB is running on port 27017
   - Check connection string in application.properties

2. **SSL Certificate Issues**

   - Verify keystore.p12 exists in resources
   - Check SSL configuration in application.properties

3. **JWT Token Issues**

   - Verify JWT secret is properly configured
   - Check token expiration settings

4. **Build Failures**
   - Use `./mvnw clean` before running
   - Ensure Java 17+ is installed

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
