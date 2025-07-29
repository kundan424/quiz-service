# Quiz Service 🎮 - Spring Boot Microservice

A microservice responsible for creating, retrieving, and submitting quizzes. This service interacts with a Question Service using **Spring Cloud OpenFeign**, stores quiz metadata in **MongoDB Atlas**, and is discoverable via **Eureka**.

---

## 📄 Table of Contents

1. [Overview](#overview)
2. [Project Structure](#project-structure)
3. [Tech Stack](#tech-stack)
4. [MongoDB Atlas Setup](#mongodb-atlas-setup)
5. [API Endpoints (Interactive)](#api-endpoints-interactive)
6. [Configuration](#configuration)
7. [How It Works](#how-it-works)
8. [Running the Project](#running-the-project)
9. [Extending the Service](#extending-the-service)

---

## ✨ Overview

The **Quiz Service** handles:

* Quiz generation based on category
* Retrieving quiz questions
* Submitting answers and scoring via another microservice (Question Service)

It uses MongoDB Atlas as a persistent backend and is built using the microservices architecture with RESTful APIs.

---

## 📁 Project Structure

```
src/
├── main/
    ├── java/com/example/quiz_service/
    │   ├── controller/         # REST endpoints
    │   ├── entity/             # Models: Quiz, QuestionWrapper, Response
    │   ├── feign/              # Feign Client for Question Service
    │   ├── repository/         # MongoDB Repositories
    │   ├── service/            # Business logic
    │   └── QuizServiceApplication.java
    └── resources/
        ├── application.properties / application.yml
        └── static/
```

---

## 🤖 Tech Stack

* **Java 21**
* **Spring Boot 3.5.3**
* **MongoDB Atlas**
* **Spring Data MongoDB**
* **Spring Cloud OpenFeign**
* **Spring Cloud Netflix Eureka Client**
* **Lombok**
* **Maven**

---

## 🪜 MongoDB Atlas Setup

1. Go to [MongoDB Atlas](https://www.mongodb.com/cloud/atlas).
2. Create a new **free-tier cluster**.
3. Create a **database user** with password.
4. Add IP access: `0.0.0.0/0` for development.
5. Get the connection URI (Example):

```
mongodb+srv://<username>:<password>@cluster0.mongodb.net/?retryWrites=true&w=majority&appName=YourApp
```

Update your `application.properties` like this:

```properties
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster0.mongodb.net/quiz_db?retryWrites=true&w=majority
spring.data.mongodb.database=quiz_db
```

---

## 🚀 API Endpoints (Interactive)

### 1. Create Quiz

* **Endpoint:** `POST /quiz/create`
* **Description:** Generates a quiz from random questions of a category
* **Params:**

  * `category` (String): e.g. `Java`
  * `numQ` (Integer): e.g. `5`
  * `title` (String): e.g. `JavaBasics`
* **Sample Request:**

```bash
curl -X POST "http://localhost:8090/quiz/create?category=Java&numQ=5&title=JavaBasics"
```

* **Response:**

```json
"success"
```

---

### 2. Get Quiz Questions

* **Endpoint:** `GET /quiz/get/{id}`
* **Description:** Get quiz questions (excluding correct answers)
* **Path Variable:** `id` (Quiz ID)
* **Sample Request:**

```bash
curl http://localhost:8090/quiz/get/64ee5f9283ba4c4aeb1a43f9
```

* **Response:**

```json
[
  {
    "id": "q1",
    "question": "What is Java?",
    "option1": "Platform",
    "option2": "IDE",
    "option3": "Language",
    "option4": "Library"
  }
]
```

---

### 3. Submit Quiz

* **Endpoint:** `POST /quiz/submit/{id}`
* **Description:** Submit answers to get score
* **Request Body:** List of `Response` objects:

```json
[
  { "id": "q1", "response": "Platform" },
  { "id": "q2", "response": "Spring" }
]
```

* **Sample Curl:**

```bash
curl -X POST http://localhost:8090/quiz/submit/64ee5f9283ba4c4aeb1a43f9 \
  -H "Content-Type: application/json" \
  -d '[{"id":"q1","response":"Platform"}]'
```

* **Response:**

```json
3
```

---

## 📊 Configuration

### application.properties (MongoDB Atlas + Eureka + Feign)

```properties
# Basic
spring.application.name=quiz-service
server.port=8090

# MongoDB Atlas
spring.data.mongodb.uri=mongodb+srv://<user>:<password>@cluster.mongodb.net/quiz_db?retryWrites=true&w=majority
spring.data.mongodb.database=quiz_db

# Eureka
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
eureka.instance.prefer-ip-address=true

# Feign
feign.hystrix.enabled=true

# Logging
logging.level.org.springframework=INFO
```

---

## ⚙️ How It Works

1. **Create Quiz:**

   * Fetches random question IDs from Question Service using Feign Client
   * Saves quiz object with title and question IDs in MongoDB

2. **Get Quiz:**

   * Retrieves quiz by ID
   * Calls Question Service to get question details

3. **Submit Quiz:**

   * Receives user answers
   * Sends them to Question Service
   * Returns score

---

## 🚧 Running the Project

```bash
# Step 1: Start Eureka Server (on port 8761)

# Step 2: Build the Quiz Service
./mvnw clean install

# Step 3: Run it
./mvnw spring-boot:run

# Step 4: Test APIs using Postman / Swagger / curl
```

---

## 🔄 Extending the Service

* Add JWT-based user authentication
* Enable question filtering (difficulty, topic, etc.)
* Add admin panel to manage quizzes
* Add Swagger UI for API docs

---

## 🔗 Related Services

* **Question Service** (Provides questions and scoring logic)
* **Service Registry (Eureka)**

---

> ✨ **Tip:** For Swagger UI, add `springdoc-openapi-ui` dependency and access: `http://localhost:8090/swagger-ui.html`
