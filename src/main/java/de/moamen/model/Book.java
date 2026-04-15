package de.moamen.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Book implements Comparable<Book>, Serializable {
    @Serial
    private static final long serialVersionUID=213123194L;
    private int isbn;
    private String title;
    private int year;
    private Author author;

    public Book() {}

    public Book(int isbn,String title, int year,Author author){
        this.isbn=isbn;
        this.title=title;
        this.year=year;
        this.author=author;
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
    public boolean equals(Object object){
        if (this==object) return true;
        if (!(object instanceof Book book)) return false;
        return this.isbn == book.getIsbn();
    }

    @Override
    public int hashCode(){
        return Objects.hash(isbn,title,year);
    }

    @Override
    public int compareTo(Book book) {
        return Integer.compare(this.year,book.getYear());
    }
}
