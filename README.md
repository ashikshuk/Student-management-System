# Student Management System

A RESTful API built with **Java (Spring Boot)** to handle comprehensive student management operations. This system implements full CRUD functionality, global exception handling, and a standardized API response structure for reliable client-server communication.

---

## 🚀 Features
* **Full CRUD Operations:** Create, Read, Update, and Delete student records seamlessly.
* **In-Memory Database:** Powered by H2 database for effortless setup with zero configuration.
* **Unified API Formatting:** Every endpoint responds using a standardized `ApiResponse` structure.
* **Global Exception Handling:** Gracefully intercepts validation errors, resource constraints, and server anomalies to return clear client-facing messages.

---

## 🛠️ Tech Stack
* **Language:** Java 17+
* **Framework:** Spring Boot (Spring Web, Spring Data JPA)
* **Database:** H2 In-Memory Database

---

## 📂 Project Structure
```text
src/main/java/com/example/studentmanagement/
│
├── controller/
│   └── StudentController.java
├── service/
│   └── StudentService.java
├── repository/
│   └── StudentRepository.java
├── model/
│   └── Student.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
└── payload/
    └── ApiResponse.java
```

---

## 📋 API Endpoints

| Method | Endpoint | Description | Status Code |
|:---|:---|:---|:---|
| **POST** | `/students` | Add a new student record | `201 Created` |
| **GET** | `/students` | Retrieve all student records | `200 OK` |
| **GET** | `/students/{id}` | Retrieve a specific student by ID | `200 OK` / `404 Not Found` |
| **PUT** | `/students/{id}` | Update details of an existing student | `200 OK` / `404 Not Found` |
| **DELETE** | `/students/{id}` | Delete a student by ID | `200 OK` / `404 Not Found` |

---

## 💎 Core Architecture Components

### 1. Unified Response Wrapper (`ApiResponse.java`)
### 2. Centralized Interceptor (`GlobalExceptionHandler.java`)

---

## 🧪 JSON Payloads Examples

### Add Student (`POST /students`)
**Request Body:**
```json
{
  "name": "Ashik Shukla",
  "email": "ashik@gmail.com",
  "course": "Computer Science",
  "age": 21
}
```

**Successful Response Structure (`201 Created`):**
```json
{
  "statusCode": 201,
  "message": "Student registered successfully.",
  "data": {
    "id": 1,
    "name": "Ashik Shukla",
    "email": "ashik@gmail.com",
    "course": "Computer Science",
    "age": 21
  }
}
```

### Get All Students (Database Empty - `GET /students`)
**Response Structure (`200 OK`):**
```json
{
  "statusCode": 200,
  "message": "No students found in the database.",
  "data": []
}
```

### Error Validation Response (`400 Bad Request`)
**Response Structure:**
```json
{
  "statusCode": 400,
  "message": "Validation failed for one or more fields.",
  "data": {
    "email": "Student email must be unique",
    "name": "Full name of the student cannot be blank"
  }
}
```

---

## 🏃 How to Run the Project
1. **Clone the Repository:**
   ```bash
   git clone <your-github-repo-url>
   cd student-management-system
   ```
2. **Build and Run:**
   ```bash
   ./mvnw spring-boot:run
   ```
3. **Database Access:**
   The H2 database console is available at `http://localhost:8080/h2-console`.
    * **JDBC URL:** `jdbc:h2:mem:testdb`
    * **Username:** `admin`
    * **Password:** `12345678`
