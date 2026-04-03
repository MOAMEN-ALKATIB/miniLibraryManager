package de.moamen.json;

import de.moamen.model.Book;

import java.util.List;

public interface BookToJson {
    void saveBookAsJsonInFile(Book book);
    List<Book> loadBooksAsJsonFromFile();
}
