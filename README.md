# 🧪 Unit Testing – Library Manager

## 📌 Overview
This part of the project is responsible for testing the core logic of the Library system using **JUnit 5**.  
It ensures that all functionalities work correctly before using them in the REST API.

---

## ⚙️ Tools Used
- Java
- JUnit 5
- Parameterized Tests (ValueSource, MethodSource)

---

## 🧩 What is tested?

- Add Book
- Delete Book
- Find Book by ISBN
- Find Book by Title (case-insensitive)
- Find Books by Author

---

## 🧪 Example Tests

### ✔ Add Book Test
```java
@Test
void testAddBook() {
    Library library = new Library();
    library.addBook(new Book(1, "Java", 2020, new Author("Moamen")));

    assertEquals(1, library.getBookList().size());
}