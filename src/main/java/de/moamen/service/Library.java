package de.moamen.service;

import de.moamen.database.BookToDatabaseConnector;
import de.moamen.database.BookToDatabaseConnectorImpl;
import de.moamen.model.Book;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> bookList;
    private static final BookToDatabaseConnector bookToDatabaseConnector=new BookToDatabaseConnectorImpl();


    public Library(){
        this.bookList=new ArrayList<>();
    }

    public void addBook(Book book){
        if (!checkIfExist(book.getIsbn())){
            bookList.add(book);
        }else {
            System.out.println("Book is already in the library");
        }

    }
    public void deleteBook(int isbn){
        Book book=findBook(isbn);
        if (book!=null){
            bookList.remove(book);
        }else{
            System.out.println("there is no book with the given isbn");
        }
    }

    private boolean checkIfExist(int isbn){
        return bookList.stream().anyMatch(book -> isbn==book.getIsbn());
    }
    public Book findBook(int isbn){
        for (Book book:bookList){
            if (book.getIsbn()==isbn)
                return book;
        }
        return null;
    }
    public Book findBook(String title) {
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
        for (Book b :
                bookList) {
            bookToDatabaseConnector.saveBook(b);
        }
    }

    public void loadFromDatabase(){
        bookList=bookToDatabaseConnector.loadBook();
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
