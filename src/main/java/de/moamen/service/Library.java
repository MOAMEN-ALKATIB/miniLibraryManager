package de.moamen.service;

import de.moamen.database.BookToDatabaseConnector;
import de.moamen.database.BookToDatabaseConnectorImpl;
import de.moamen.exceptions.LoadingFailedException;
import de.moamen.exceptions.NoBookFoundException;
import de.moamen.json.BookToJson;
import de.moamen.json.BookToJsonImpl;
import de.moamen.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.moamen.io.IOImpl;
import de.moamen.io.IOInterface;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private static List<Book> bookList;
    private static final IOInterface io = new IOImpl();
    private static final BookToJson bookToJson = new BookToJsonImpl();
    private static final BookToDatabaseConnector bookToDatabaseConnector = new BookToDatabaseConnectorImpl();

    private static final Logger logger = LoggerFactory.getLogger(Library.class);

    public Library() {
        bookList = new ArrayList<>();
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

    public static void saveInDatabase() {
        logger.debug("Saving {} books to database", bookList.size());
        for (Book b :
                bookList) {
            bookToDatabaseConnector.saveBook(b);
        }
        logger.info("Successfully saved {} books to database", bookList.size());
    }

    public static void loadFromDatabase() {
        logger.debug("Loading books from database");
        bookList = bookToDatabaseConnector.loadBook();
        logger.info("Loaded {} books from database", bookList.size());
    }

    public void saveBooksInFiles() {
        logger.debug("Saving {} books in Files", bookList.size());
        if (!bookList.isEmpty()) {
            for (Book book : bookList) {
                io.save(book);
            }
        }
        logger.info("Successfully saved {} books", bookList.size());
    }

    public void loadBooksFromFiles() {
        logger.debug("Loading books from Files");
        try {
            bookList = io.load();
        } catch (NoBookFoundException | LoadingFailedException e) {
            System.out.println(e.getMessage());
        }
        logger.info("Loaded {} books from Files", bookList.size());

    }
}
