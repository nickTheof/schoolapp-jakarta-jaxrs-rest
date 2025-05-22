# SchoolApp REST API

A secure RESTful API for managing educational entities (e.g., cities, users, students) built using **Jakarta EE 10**, with **JWT authentication** and MySQL Database persistence.

---

## 🚀 Features

- ✅ JWT-based Authentication (Login/Register)
- ✅ Role-based Authorization (`ADMIN`, `EDITOR`, `READER`)
- ✅ Stateless security — role extracted from JWT claims
- ✅ DTO-based validation and custom exception handling
- ✅ Layered architecture (Controller / Service / DAO)
- ✅ Clean architecture 
---

## 🔐 Authentication & Authorization

- JWTs are signed using HS256
- Role is stored in the token claims
- Requests are authenticated via a `Bearer <token>` header

---

## 🧱 Technologies

- Jakarta EE 10 (JAX-RS, CDI, JPA)
- Jersey (for REST API)
- JPA-Hibernate-MySQL (ORM)
- HikariCP (connection pooling)
- Lombok (boilerplate reduction)
- JJWT (JWT handling)
- SLF4J / Logback (logging)
- JBCRYPT (One-way hashing passwords)

---

## 🛠️ Getting Started

### Prerequisites

---

- JDK 17
- Jakarta EE 10
- Apache Tomcat 10.1

# **Installation**

---

## **Manual Upload**

1. Just run 'mvn clean package' in root dir of the app
2. Start the web server with startup.bat (Windows) or startup.sh (linux/macOS)
3. Copy /target/school-app-jakarta-rest-1.0-SNAPSHOT.war in webapps folder of the Server

