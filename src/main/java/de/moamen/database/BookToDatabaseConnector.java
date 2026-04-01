package de.moamen.database;

import de.moamen.model.Book;

import java.util.List;

public interface BookToDatabaseConnector {
    void saveBook(Book book);

    List<Book> loadBook();
}
