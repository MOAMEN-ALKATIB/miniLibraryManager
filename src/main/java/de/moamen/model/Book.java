package de.moamen.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Book implements Comparable<Book>, Serializable {
    @Serial
    private static final long serialVersionUID = 213123194L;
    private int isbn;
    private String title;
    private int year;
    private Author author;

    public Book() {
    }

    public Book(int isbn, String title, int year, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.author = author;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn=" + isbn +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", author=" + author +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Book book)) return false;
        return this.isbn == book.getIsbn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, year);
    }

    @Override
    public int compareTo(Book book) {
        return Integer.compare(this.year, book.getYear());
    }
}
