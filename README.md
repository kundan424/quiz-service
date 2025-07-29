# 🧠 Quiz Service Microservice

<div align="center">

![Quiz Service](https://img.shields.io/badge/Quiz%20Service-Microservice-blue?style=for-the-badge&logo=spring)

**A Spring Boot microservice for managing quizzes with MongoDB Atlas and Feign Client integration**

[![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen?style=flat-square&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![MongoDB Atlas](https://img.shields.io/badge/MongoDB-Atlas-green?style=flat-square&logo=mongodb)](https://www.mongodb.com/atlas)
[![Feign Client](https://img.shields.io/badge/Feign-Client-blue?style=flat-square&logo=spring)](https://spring.io/projects/spring-cloud-openfeign)

[🚀 Quick Start](#-quick-start) • [📚 API Testing](#-interactive-api-testing) • [⚙️ Configuration](#-configuration) • [🧪 Testing](#-testing)

</div>

---

## ✨ Overview

The **Quiz Service** is a Spring Boot microservice that provides comprehensive quiz management functionality. It creates quizzes, retrieves questions, and handles submissions with automatic scoring through integration with a Question Service via Feign Client.

### 🎯 Key Features

<table>
<tr>
<td width="50%">

### 📋 **Quiz Management**
- ✅ Create quizzes with random questions from categories
- ✅ Retrieve quiz questions by ID
- ✅ Submit answers with automatic scoring
- ✅ MongoDB Atlas integration for persistence

</td>
<td width="50%">

### 🔧 **Microservice Architecture**
- ✅ Feign Client for inter-service communication
- ✅ Eureka service discovery integration
- ✅ Circuit breaker pattern with Hystrix
- ✅ RESTful API design

</td>
</tr>
</table>

## 🛠️ Technology Stack

<div align="center">

| Technology | Version | Purpose |
|------------|---------|---------|
| ![Java](https://img.shields.io/badge/Java-21-orange?style=flat&logo=openjdk) | 21 | Core Language |
| ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen?style=flat&logo=spring-boot) | 3.5.3 | Framework |
| ![MongoDB Atlas](https://img.shields.io/badge/MongoDB-Atlas-green?style=flat&logo=mongodb) | Cloud | Database |
| ![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-OpenFeign-blue?style=flat) | Latest | Service Communication |
| ![Eureka](https://img.shields.io/badge/Netflix-Eureka-red?style=flat) | Latest | Service Discovery |
| ![Lombok](https://img.shields.io/badge/Lombok-Latest-red?style=flat) | Latest | Code Reduction |

</div>

## 🚀 Quick Start

### 📋 Prerequisites

<details>
<summary>🔧 Click to expand requirements</summary>

- **Java Development Kit (JDK) 21+**
- **Maven 3.8+**
- **MongoDB Atlas account** (free tier available)
- **Question Service** (running separately)
- **Eureka Server** (optional, for service discovery)

</details>

### ⚡ Installation & Setup

<details>
<summary>🚀 Step-by-step installation guide</summary>

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd quiz-service
   ```

2. **Configure MongoDB Atlas**
   
   Create a `.env` file or update `application.yml`:
   ```yaml
   spring:
     data:
       mongodb:
         uri: mongodb+srv://username:password@cluster.mongodb.net/quiz_db
         database: quiz_db
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Verify the service**
   ```bash
   curl http://localhost:8090/actuator/health
   ```

</details>

## ⚙️ Configuration

### 🗂️ Application Properties Setup

<details>
<summary><strong>📝 application.properties Configuration</strong></summary>

```properties
# ========================================
# APPLICATION IDENTIFICATION
# ========================================
spring.application.name=quiz-service
server.port=8090

# ========================================
# MONGODB ATLAS CONFIGURATION
# ========================================
spring.data.mongodb.uri=mongodb+srv://username:password@your-cluster.mongodb.net/quiz_db?retryWrites=true&w=majority
spring.data.mongodb.database=quiz_db

# Connection pool settings
spring.data.mongodb.connections-per-host=10
spring.data.mongodb.min-connections-per-host=5
spring.data.mongodb.max-wait-time=30000
spring.data.mongodb.connect-timeout=10000
spring.data.mongodb.socket-timeout=0

# ========================================
# EUREKA CLIENT CONFIGURATION
# ========================================
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.instance.prefer-ip-address=true
eureka.instance.lease-renewal-interval-in-seconds=30

# ========================================
# FEIGN CLIENT CONFIGURATION
# ========================================
feign.hystrix.enabled=true
feign.client.config.default.connect-timeout=10000
feign.client.config.default.read-timeout=10000

# ========================================
# LOGGING CONFIGURATION
# ========================================
logging.level.org.springframework=INFO
logging.level.com.example.quiz_service=DEBUG
logging.level.feign=DEBUG

# ========================================
# ACTUATOR ENDPOINTS
# ========================================
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=always
```

</details>

<details>
<summary><strong>🔧 application.yml Configuration (Alternative)</strong></summary>

```yaml
# ========================================
# APPLICATION CONFIGURATION
# ========================================
spring:
  application:
    name: quiz-service
  data:
    mongodb:
      uri: mongodb+srv://username:password@your-cluster.mongodb.net/quiz_db?retryWrites=true&w=majority
      database: quiz_db

server:
  port: 8090

# ========================================
# EUREKA CLIENT CONFIGURATION
# ========================================
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
    register-with-eureka: true
    fetch-registry: true
  instance:
    prefer-ip-address: true
    lease-renewal-interval-in-seconds: 30

# ========================================
# FEIGN CLIENT CONFIGURATION
# ========================================
feign:
  hystrix:
    enabled: true
  client:
    config:
      default:
        connect-timeout: 10000
        read-timeout: 10000

# ========================================
# LOGGING CONFIGURATION
# ========================================
logging:
  level:
    org.springframework: INFO
    com.example.quiz_service: DEBUG
    feign: DEBUG

# ========================================
# ACTUATOR CONFIGURATION
# ========================================
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      show-details: always
```

</details>

### 🔐 MongoDB Atlas Setup

<details>
<summary><strong>☁️ MongoDB Atlas Configuration Guide</strong></summary>

1. **Create MongoDB Atlas Account**
   - Visit [MongoDB Atlas](https://www.mongodb.com/atlas)
   - Sign up for a free account

2. **Create a Cluster**
   - Choose "Build a Database"
   - Select "M0 Sandbox" (Free)
   - Choose your cloud provider and region

3. **Configure Database Access**
   - Go to "Database Access"
   - Click "Add New Database User"
   - Create username and password
   - Grant "Read and write to any database" privileges

4. **Configure Network Access**
   - Go to "Network Access"
   - Click "Add IP Address"
   - Add your current IP or "0.0.0.0/0" for development

5. **Get Connection String**
   - Go to "Databases"
   - Click "Connect" on your cluster
   - Choose "Connect your application"
   - Copy the connection string and replace `<password>` with your password

</details>

## 📚 Interactive API Testing

### 🎮 API Endpoints Overview

<div align="center">

| Method | Endpoint | Description | Status |
|--------|----------|-------------|--------|
| `POST` | `/quiz/create` | Create a new quiz | ✅ |
| `GET` | `/quiz/get/{id}` | Get quiz questions | ✅ |
| `POST` | `/quiz/submit/{id}` | Submit quiz answers | ✅ |

</div>

---

### 1️⃣ **CREATE QUIZ**

<details>
<summary><strong>📝 POST /quiz/create - Create a New Quiz</strong></summary>

**Endpoint:** `POST http://localhost:8090/quiz/create`

**Description:** Creates a new quiz by selecting random questions from a specified category.

**Query Parameters:**
- `category` (String, **required**): Question category (e.g., "Java", "Spring", "Python")
- `numQ` (Integer, **required**): Number of questions (1-50)
- `title` (String, **required**): Quiz title

#### 🧪 **Test with cURL**
```bash
# Create a Java quiz with 5 questions
curl -X POST "http://localhost:8090/quiz/create?category=Java&numQ=5&title=Java%20Basics%20Quiz" \
  -H "Content-Type: application/json"
```

#### 🧪 **Test with PowerShell**
```powershell
Invoke-RestMethod -Uri "http://localhost:8090/quiz/create?category=Java&numQ=5&title=Java Basics Quiz" -Method POST
```

#### 🧪 **Test with Postman**
```
Method: POST
URL: http://localhost:8090/quiz/create
Params:
  - category: Java
  - numQ: 5
  - title: Java Basics Quiz
```

#### 📋 **Sample Responses**
```json
// Success Response (201 Created)
"success"

// Error Response (500 Internal Server Error)
"failure"
```

#### 🔍 **Response Codes**
- `201 Created`: Quiz created successfully
- `500 Internal Server Error`: Quiz creation failed (check logs)

</details>

---

### 2️⃣ **GET QUIZ QUESTIONS**

<details>
<summary><strong>📖 GET /quiz/get/{id} - Retrieve Quiz Questions</strong></summary>

**Endpoint:** `GET http://localhost:8090/quiz/get/{id}`

**Description:** Retrieves the list of questions (without correct answers) for a specific quiz.

**Path Parameters:**
- `id` (String, **required**): Quiz identifier

#### 🧪 **Test with cURL**
```bash
# Get quiz questions (replace {quiz-id} with actual ID)
curl -X GET "http://localhost:8090/quiz/get/60f7b1b5e4b0a73d5c8b4567" \
  -H "Accept: application/json"
```

#### 🧪 **Test with PowerShell**
```powershell
$quizId = "60f7b1b5e4b0a73d5c8b4567"
Invoke-RestMethod -Uri "http://localhost:8090/quiz/get/$quizId" -Method GET
```

#### 🧪 **Test with JavaScript (Fetch)**
```javascript
const quizId = "60f7b1b5e4b0a73d5c8b4567";
fetch(`http://localhost:8090/quiz/get/${quizId}`)
  .then(response => response.json())
  .then(data => console.log(data));
```

#### 📋 **Sample Response**
```json
[
  {
    "id": "q1",
    "questionTitle": "What is polymorphism in Java?",
    "option1": "Method overloading",
    "option2": "Method overriding", 
    "option3": "Both A and B",
    "option4": "None of the above"
  },
  {
    "id": "q2",
    "questionTitle": "Which keyword is used for inheritance in Java?",
    "option1": "extends",
    "option2": "implements",
    "option3": "inherits",
    "option4": "derive"
  }
]
```

#### 🔍 **Response Codes**
- `200 OK`: Quiz questions retrieved successfully
- `404 Not Found`: Quiz not found
- `500 Internal Server Error`: Server error

</details>

---

### 3️⃣ **SUBMIT QUIZ ANSWERS**

<details>
<summary><strong>✅ POST /quiz/submit/{id} - Submit Quiz Answers</strong></summary>

**Endpoint:** `POST http://localhost:8090/quiz/submit/{id}`

**Description:** Submits user answers for a quiz and returns the calculated score.

**Path Parameters:**
- `id` (String, **required**): Quiz identifier

**Request Body:** Array of Response objects
```json
[
  {
    "id": "question_id",
    "response": "user_answer"
  }
]
```

#### 🧪 **Test with cURL**
```bash
# Submit quiz answers
curl -X POST "http://localhost:8090/quiz/submit/60f7b1b5e4b0a73d5c8b4567" \
  -H "Content-Type: application/json" \
  -d '[
    {"id": "q1", "response": "Both A and B"},
    {"id": "q2", "response": "extends"}
  ]'
```

#### 🧪 **Test with PowerShell**
```powershell
$quizId = "60f7b1b5e4b0a73d5c8b4567"
$body = @(
    @{id="q1"; response="Both A and B"},
    @{id="q2"; response="extends"}
) | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8090/quiz/submit/$quizId" -Method POST -Body $body -ContentType "application/json"
```

#### 🧪 **Test with Postman**
```
Method: POST
URL: http://localhost:8090/quiz/submit/60f7b1b5e4b0a73d5c8b4567
Headers:
  Content-Type: application/json
Body (raw JSON):
[
  {
    "id": "q1",
    "response": "Both A and B"
  },
  {
    "id": "q2", 
    "response": "extends"
  }
]
```

#### 📋 **Sample Response**
```json
// Score out of total questions
2
```

#### 🔍 **Response Codes**
- `200 OK`: Quiz submitted successfully, returns score
- `404 Not Found`: Quiz not found
- `500 Internal Server Error`: Submission failed

</details>

## 🏗️ Project Architecture

<details>
<summary><strong>🔧 Project Structure</strong></summary>

```
quiz-service/
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/
│   │   │   └── 📁 com/example/quiz_service/
│   │   │       ├── 🚀 QuizServiceApplication.java
│   │   │       ├── 📁 controller/
│   │   │       │   └── 🎮 QuizController.java
│   │   │       ├── 📁 entity/
│   │   │       │   ├── 📋 Quiz.java
│   │   │       │   ├── 📋 QuestionWrapper.java
│   │   │       │   └── 📋 Response.java
│   │   │       ├── 📁 feign/
│   │   │       │   └── 🌐 QuizInterface.java
│   │   │       ├── 📁 repository/
│   │   │       │   └── 🗄️ QuizRepository.java
│   │   │       └── 📁 service/
│   │   │           └── 🔧 QuizService.java
│   │   └── 📁 resources/
│   │       ├── ⚙️ application.yml
│   │       └── ⚙️ application.properties
│   └── 📁 test/
├── 📄 pom.xml
├── 🐳 Dockerfile
└── 📖 README.md
```

</details>

<details>
<summary><strong>🔄 Service Flow Diagram</strong></summary>

```mermaid
graph TB
    A[Client Request] --> B[Quiz Controller]
    B --> C[Quiz Service]
    C --> D{Operation Type}
    
    D -->|Create Quiz| E[Feign Client]
    E --> F[Question Service]
    F --> G[Random Questions]
    G --> H[MongoDB Atlas]
    
    D -->|Get Quiz| I[MongoDB Atlas]
    I --> J[Quiz Data]
    J --> K[Feign Client]
    K --> L[Question Service]
    L --> M[Question Details]
    
    D -->|Submit Quiz| N[Feign Client]
    N --> O[Question Service]
    O --> P[Score Calculation]
    
    H --> Q[Response]
    M --> Q
    P --> Q
    Q --> R[Client Response]
```

</details>

## 🧪 Testing

### 🔬 Automated Testing

<details>
<summary><strong>🧪 Running Tests</strong></summary>

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=QuizServiceTest

# Run tests with coverage
mvn test jacoco:report

# Integration tests
mvn verify
```

</details>

### 🎯 Manual API Testing Scenarios

<details>
<summary><strong>📋 Complete Testing Workflow</strong></summary>

#### **Scenario 1: Create and Take a Java Quiz**

1. **Create Quiz**
   ```bash
   curl -X POST "http://localhost:8090/quiz/create?category=Java&numQ=3&title=Java%20Fundamentals"
   ```
   Expected: `"success"` and status `201`

2. **Get Quiz Questions** (use returned quiz ID)
   ```bash
   curl -X GET "http://localhost:8090/quiz/get/YOUR_QUIZ_ID"
   ```
   Expected: Array of 3 question objects

3. **Submit Answers**
   ```bash
   curl -X POST "http://localhost:8090/quiz/submit/YOUR_QUIZ_ID" \
     -H "Content-Type: application/json" \
     -d '[{"id":"q1","response":"option1"},{"id":"q2","response":"option2"}]'
   ```
   Expected: Score number (0-3)

#### **Scenario 2: Error Handling**

1. **Invalid Quiz ID**
   ```bash
   curl -X GET "http://localhost:8090/quiz/get/invalid-id"
   ```
   Expected: `404 Not Found`

2. **Empty Category**
   ```bash
   curl -X POST "http://localhost:8090/quiz/create?category=&numQ=5&title=Test"
   ```
   Expected: `500 Internal Server Error`

</details>

## 🐳 Docker Deployment

<details>
<summary><strong>🚀 Docker Setup</strong></summary>

### Dockerfile
```dockerfile
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/quiz-service-*.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Docker Compose
```yaml
version: '3.8'
services:
  quiz-service:
    build: .
    ports:
      - "8090:8090"
    environment:
      - SPRING_DATA_MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/quiz_db
      - EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka-server:8761/eureka/
    depends_on:
      - eureka-server

  eureka-server:
    image: steeltoeoss/eureka-server
    ports:
      - "8761:8761"
```

### Build and Run
```bash
# Build the application
mvn clean package

# Build Docker image
docker build -t quiz-service .

# Run with Docker Compose
docker-compose up -d
```

</details>

## 🔧 Troubleshooting

<details>
<summary><strong>🚨 Common Issues & Solutions</strong></summary>

### **MongoDB Connection Issues**
```bash
# Check connectivity
curl -X GET "http://localhost:8090/actuator/health"

# Verify MongoDB Atlas IP whitelist
# Ensure connection string is correct
# Check username/password
```

### **Feign Client Errors**
```bash
# Check if Question Service is running
curl -X GET "http://question-service:8080/actuator/health"

# Verify Eureka registration
curl -X GET "http://localhost:8761/eureka/apps"
```

### **Service Discovery Issues**
```bash
# Check Eureka server
curl -X GET "http://localhost:8761"

# Verify service registration
curl -X GET "http://localhost:8761/eureka/apps/QUIZ-SERVICE"
```

</details>

## 🤝 Contributing

<details>
<summary><strong>👨‍💻 How to Contribute</strong></summary>

1. **Fork the repository**
2. **Create feature branch**: `git checkout -b feature/amazing-feature`
3. **Make changes and test**:
   ```bash
   mvn test
   mvn spring-boot:run
   # Test your changes
   ```
4. **Commit changes**: `git commit -m 'Add amazing feature'`
5. **Push to branch**: `git push origin feature/amazing-feature`
6. **Create Pull Request**

### **Development Guidelines**
- Follow Spring Boot best practices
- Add unit tests for new features
- Update documentation
- Use meaningful commit messages

</details>

## 📞 Support & Contact

<div align="center">

### Need Help? 

[![GitHub Issues](https://img.shields.io/badge/GitHub-Issues-red?style=for-the-badge&logo=github)](https://github.com/yourusername/quiz-service/issues)
[![Documentation](https://img.shields.io/badge/Docs-Wiki-blue?style=for-the-badge&logo=gitbook)](https://github.com/yourusername/quiz-service/wiki)

**Found a bug?** [Create an issue](https://github.com/yourusername/quiz-service/issues/new)  
**Need help?** Check our [troubleshooting guide](#-troubleshooting)  
**Want to contribute?** See our [contributing guidelines](#-contributing)

</div>

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

<div align="center">

### ⭐ Show Your Support

If you find this project helpful, please consider giving it a star!

[![GitHub stars](https://img.shields.io/github/stars/yourusername/quiz-service?style=social)](https://github.com/yourusername/quiz-service/stargazers)

**Made with ❤️ for the microservices community**

</div>