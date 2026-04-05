package de.moamen.io;

import de.moamen.exceptions.LoadingFailedException;
import de.moamen.exceptions.NoBookFoundException;
import de.moamen.model.Book;

import java.util.List;

public interface IOInterface {
    void save(Book book);
    List<Book> load() throws NoBookFoundException, LoadingFailedException;
}
