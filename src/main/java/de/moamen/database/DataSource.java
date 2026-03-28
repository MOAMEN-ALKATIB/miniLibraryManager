package de.moamen.database;

import de.moamen.model.Author;
import de.moamen.model.Book;

import java.util.List;

public interface DataSource {
    boolean open();

    void close();

    List<Book> bookQuery();

    void insertBook(Book book);

    Book findBook(int isbn);
}
