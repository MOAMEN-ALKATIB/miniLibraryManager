package de.moamen.controller;

import de.moamen.model.Author;
import de.moamen.model.Book;
import de.moamen.service.Library;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Cli {
    private static final Library library;
    private static final String COMMANDS;
    private static final Scanner scanner;

    static {
        library=new Library();
        scanner=new Scanner(System.in);
        COMMANDS = """
                    (1) Add book
                    (2) Delete book
                    (3) find Book by title
                    (4) find book by isbn
                    (5) display all books
                    (6) display author books
                    (7) sort books by year
                    (8) sort books by title
                    (9) End program""";
    }

    public static void cli() {
        System.out.println(COMMANDS);
        int userInput=getUserInput("select an option between 1-9",1,9);
        while (userInput!=9){
            switch (userInput){
                case 1 -> library.addBook(createBook());
                case 2 -> {
                    System.out.print("isbn:");
                    int isbn=getUserInput("",Integer.MIN_VALUE,Integer.MAX_VALUE);
                    library.deleteBook(isbn);
                }
                case 3 -> {
                    System.out.print("Book title:");
                    String title=scanner.nextLine();
                    Book book=library.findBook(title);
                    if (book!=null){
                        System.out.println(book);
                    }else {
                        System.out.println("there is no book with the given title");
                    }
                }
                case 4 -> {
                    System.out.print("isbn:");
                    int isbn=getUserInput("",Integer.MIN_VALUE,Integer.MAX_VALUE);
                    Book book=library.findBook(isbn);
                    if (book!=null){
                        System.out.println(book);
                    }else {
                        System.out.println("there is no book with the given isbn");
                    }
                }
                case 5 -> library.displayALlBooks();
                case 6 -> {
                    System.out.print("author name:");
                    String authorName=scanner.nextLine();
                    library.displayAuthorBooks(authorName);
                }
                case 7 -> Collections.sort(library.getBookList());
                case 8 -> library.getBookList().sort(Comparator.comparing(Book::getTitle));
                default -> {}
            }
            System.out.println(COMMANDS);
            userInput=getUserInput("select an option between 1-9",1,9);
        }
    }

    private static int getUserInput(String message, int minValue, int maxValue){
        int userInput;
        while (true){
            if (scanner.hasNextInt()){
                userInput=scanner.nextInt();
                scanner.nextLine();
                if (userInput<minValue || userInput>maxValue){
                    System.out.println(message);
                }else{
                    break;
                }
            }else{
                System.out.println("you have to enter a number only!!!");
            }
        }
        return userInput;
    }

    private static Book createBook(){
        System.out.print("Book title:");
        String title=scanner.nextLine();
        System.out.print("Book year:");
        int year=getUserInput("the year cant be negative or a value higher than 2026",0,2026);
        System.out.print("Author name:");

        List<Book> bookList=library.getBookList();
        String authorName=scanner.nextLine();
        for (Book book:bookList){
            if (book.getAuthor().getName().equalsIgnoreCase(authorName))
                return new Book(title,year,book.getAuthor());
        }
        return new Book(title,year,new Author(authorName));
    }
}
