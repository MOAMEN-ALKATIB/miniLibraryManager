package de.moamen.service;

import de.moamen.database.DataSource;
import de.moamen.database.DataSourceImpl;
import de.moamen.model.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LibraryService {
    private final DataSource dataSource = new DataSourceImpl();

    public void insertBook(Book book) {
        if (dataSource.open()) {
            dataSource.insertBook(book);
            dataSource.close();
        }
    }

    public Book deleteBook(int isbn) {
        Book book = null;
        if (dataSource.open()) {
            book = dataSource.findBook(isbn);
            dataSource.deleteBook(isbn);
            dataSource.close();
        }
        return book;
    }

    public Book findBookByISBN(int isbn) {
        if (dataSource.open()) {
            Book book = dataSource.findBook(isbn);
            dataSource.close();
            return book;
        }
        return null;
    }

    public Book findBookByTitle(String title) {
        try {
            if (dataSource.open()) {
                return dataSource.bookQuery()
                        .stream()
                        .filter(b -> b.getTitle().equalsIgnoreCase(title))
                        .findFirst()
                        .orElse(null);
            }
            return null;
        } finally {
            dataSource.close();
        }
    }

    public List<Book> findBooksByAuthor(String authorName) {
        try {
            if (dataSource.open()) {
                return dataSource.bookQuery()
                        .stream()
                        .filter(b -> b.getAuthor().getName().equalsIgnoreCase(authorName))
                        .toList();
            }
            return null;
        } finally {
            dataSource.close();
        }
    }

    public List<Book> displayBooks() {
        List<Book> books = new ArrayList<>();
        if (dataSource.open()) {
            books = dataSource.bookQuery();
            dataSource.close();
        }
        return books;
    }

    public List<Book> sortBooksByYear() {
        return displayBooks()
                .stream()
                .sorted(Comparator.comparingInt(Book::getYear))
                .toList();
    }

    public List<Book> sortBooksByTitle() {
        return displayBooks()
                .stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .toList();

    }
}
