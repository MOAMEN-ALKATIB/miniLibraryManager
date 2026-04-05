# Java Library Management System

A simple **Library Management System** built using **pure Java (no Spring)** to practice core backend concepts such as OOP, JDBC, JSON processing, logging, and clean architecture.

---

## 🚀 Project Goals

This project was created to practice and demonstrate:

- Object-Oriented Programming (OOP)
- Clean Architecture
- JDBC Database Integration
- JSON Serialization / Deserialization
- Logging
- File I/O
- Generics
- Modular Project Structure

---

## 🧩 Features Implemented

### 1. Core Library Management
- Add Book
- Delete Book
- Find Book by ISBN
- Find Book by Title
- Display all books
- Display books by author
- sort books by year
- sort books by their titles

### 2. Database Integration (SQLite via JDBC)
- Custom DataSource implementation
- Automatic table creation (`CREATE TABLE IF NOT EXISTS`)
- Insert book into database
- Find book from database
- Query multiple books
- JDBC connection handling

### 3. Logging
Implemented using:

- SLF4J
- Log4j2

Logging added to:

- Library service
- Database connector
- DataSource implementation
- JSON converter

Log Levels used:

- DEBUG
- INFO
- WARN
- ERROR

---

### 4. JSON Support
Implemented using:

- Jackson

Generic JSON converter:

```java
<T> String toJson(T obj)
<T> T fromJson(String json, Class<T> clazz)