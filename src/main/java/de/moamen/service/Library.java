package de.moamen.service;

import de.moamen.database.BookToDatabaseConnector;
import de.moamen.database.BookToDatabaseConnectorImpl;
import de.moamen.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> bookList;
    private static final BookToDatabaseConnector bookToDatabaseConnector=new BookToDatabaseConnectorImpl();
    private static final Logger logger= LoggerFactory.getLogger(Library.class);

    public Library(){
        this.bookList=new ArrayList<>();
    }

    public void addBook(Book book){
        logger.debug("Attempting to add book {}", book);
        if (!checkIfExist(book.getIsbn())){
            bookList.add(book);
            logger.info("book added successfully: {}",book);
        }else {
            logger.warn("Book is already in the library: {}", book);
        }

    }
    public void deleteBook(int isbn){
        logger.debug("Attempting to delete book with isbn {}",isbn);
        Book book=findBook(isbn);
        if (book!=null){
            bookList.remove(book);
            logger.info("book deleted successfully: {}",book);
        }else{
            logger.warn("Cannot delete book. No book found with isbn {}", isbn);
        }
    }

    private boolean checkIfExist(int isbn){
        return bookList.stream().anyMatch(book -> isbn==book.getIsbn());
    }
    public Book findBook(int isbn){
        logger.debug("Checking if book with isbn {} exists", isbn);
        for (Book book:bookList){
            if (book.getIsbn()==isbn){
                logger.info("book with the isbn:{} found",isbn);
                return book;
            }
        }
        logger.warn("no book with the isbn {} was found",isbn);
        return null;
    }
    public Book findBook(String title) {
        logger.debug("Searching for book with title {}", title);
       return bookList.stream().filter(book -> book.getTitle()!=null && title.equalsIgnoreCase(book.getTitle())).findFirst().orElse(null);
    }

    public void displayAuthorBooks(String authorName){
        for (Book book:bookList){
            if (book.getAuthor().getName().equalsIgnoreCase(authorName)){
                System.out.println(book);
            }
        }
    }

    public void displayALlBooks(){
        for (Book book:bookList){
            System.out.println(book);
        }
    }

    public void saveInDatabase(){
        logger.debug("Saving {} books to database", bookList.size());
        for (Book b :
                bookList) {
            bookToDatabaseConnector.saveBook(b);
        }
        logger.info("Successfully saved {} books to database", bookList.size());
    }

    public void loadFromDatabase(){
        logger.debug("Loading books from database");
        bookList=bookToDatabaseConnector.loadBook();
        logger.info("Loaded {} books from database", bookList.size());
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
