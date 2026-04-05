package de.moamen.service;

import de.moamen.json.BookToJson;
import de.moamen.json.BookToJsonImpl;
import de.moamen.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> bookList;
    private static final BookToJson bookToJson = new BookToJsonImpl();
    private static final Logger logger = LoggerFactory.getLogger(Library.class);

    public Library() {
        this.bookList = new ArrayList<>();
    }

    public void addBook(Book book) {
        logger.debug("Attempting to add book {}", book);
        if (!checkIfExist(book.getIsbn())) {
            bookList.add(book);
            logger.info("book added successfully: {}", book);
        } else {
            logger.warn("Book is already in the library: {}", book);
        }

    }

    public void deleteBook(int isbn) {
        logger.debug("Attempting to delete book with isbn {}", isbn);
        Book book = findBook(isbn);
        if (book != null) {
            bookList.remove(book);
            logger.info("book deleted successfully: {}", book);
        } else {
            logger.warn("Cannot delete book. No book found with isbn {}", isbn);
        }
    }

    private boolean checkIfExist(int isbn) {
        return bookList.stream().anyMatch(book -> isbn == book.getIsbn());
    }

    public Book findBook(int isbn) {
        logger.debug("Checking if book with isbn {} exists", isbn);
        for (Book book : bookList) {
            if (book.getIsbn() == isbn) {
                logger.info("book with the isbn:{} found", isbn);
                return book;
            }
        }
        logger.warn("no book with the isbn {} was found", isbn);
        return null;
    }

    public Book findBook(String title) {
        logger.debug("Searching for book with title {}", title);
        return bookList.stream().filter(book -> book.getTitle() != null && title.equalsIgnoreCase(book.getTitle())).findFirst().orElse(null);
    }

    public List<Book> findBooksByAuthor(String authorName) {
        logger.debug("Searching for the Author {} books", authorName);
        return bookList.stream()
                .filter(book -> book.getAuthor().getName().equalsIgnoreCase(authorName))
                .toList();
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void saveAsJsonInFile() {
        logger.debug("Attempting to save books as json");
        for (Book book : bookList) {
            bookToJson.saveBookAsJsonInFile(book);
        }
    }

    public void loadFromJsonFile() {
        logger.debug("Attempting to load books from the json-file");
        bookList = bookToJson.loadBooksAsJsonFromFile();
        logger.info("Loaded {} books from Json-file", bookList.size());
    }
}
