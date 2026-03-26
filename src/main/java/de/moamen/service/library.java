package de.moamen.service;

import de.moamen.model.Book;

import java.util.ArrayList;
import java.util.List;

public class library {
    private final List<Book> bookList;

    public library(){
        this.bookList=new ArrayList<>();
    }

    public void addBook(Book book){
        if (!checkIfExist(book.getId())){
            bookList.add(book);
        }else {
            System.out.println("Book is already in the library");
        }

    }
    public void deleteBook(int id){
        Book book=findBook(id);
        if (book!=null){
            bookList.remove(book);
        }else{
            System.out.println("there is no book with the given id");
        }
    }

    private boolean checkIfExist(int id){
        return bookList.stream().anyMatch(book -> id==book.getId());
    }
    public Book findBook(int id){
        for (Book book:bookList){
            if (book.getId()==id)
                return book;
        }
        return null;
    }
    public Book findBook(String title) {
       return bookList.stream().filter(book -> book.getTitle()!=null && title.equalsIgnoreCase(book.getTitle())).findFirst().orElse(null);
    }

    public List<Book> getBookList() {
        return bookList;
    }
}
