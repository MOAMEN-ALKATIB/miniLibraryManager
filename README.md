## 🌐 REST API - Mini Library Manager

This branch contains the implementation of a **RESTful API** for the Mini Library Manager project using **Jakarta EE (JAX-RS)**.

---

### 🚀 Features

* 📚 Manage books (Create, Read, Delete)
* 🔍 Search books by:

    * ISBN
    * Title
    * Author
* 📊 Sort books by:

    * Year
    * Title
* 🗄️ SQLite database integration (JDBC)
* 🔄 JSON-based communication

---

### 🛠️ Technologies Used

* Java 17
* Jakarta EE (JAX-RS / Jersey)
* Maven
* SQLite (JDBC)
* JSON (Jackson)

---

### 📡 API Endpoints

#### 📘 Get all books

```
GET /api/books/all
```

#### ➕ Insert book

```
POST /api/books/insert
```

#### ❌ Delete book

```
DELETE /api/books/{isbn}
```

#### 🔍 Get book by ISBN

```
GET /api/books/{isbn}
```

#### 🔎 Search by title

```
GET /api/books/search?title=...
```

#### 👤 Search by author

```
GET /api/books/author?authorName=...
```

#### 📊 Sort by year

```
GET /api/books/sort/year
```

#### 🔤 Sort by title

```
GET /api/books/sort/title
```

---

### ▶️ How to Run

1. Build the project:

```
mvn clean package
```

2. Run with Tomcat (via Cargo):

```
mvn cargo:run
```

3. Open in browser:

```
http://localhost:8080/miniLibraryManager/
```

---

### ⚠️ Notes

* API responses are in JSON format.
* Error handling is implemented using HTTP status codes (e.g., 404 for not found).

---

### 📌 Purpose

This branch focuses on practicing:

* REST API design
* Backend development with Java
* Database integration
* Clean architecture principles
