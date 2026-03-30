package de.moamen;

import de.moamen.controller.Cli;
import de.moamen.exceptions.LoadingFailedException;
import de.moamen.exceptions.NoBookFoundException;
import de.moamen.io.IOImpl;
import de.moamen.io.IOInterface;
import de.moamen.model.Author;
import de.moamen.model.Book;

import java.util.List;

public class Main {
    public static void main(String[] args)  {
        //Cli.cli();
        IOInterface io=new IOImpl();

        try{
            List<Book> bookList=io.load();
            for(Book book:bookList){
                System.out.println(book);
            }
        }catch (NoBookFoundException | LoadingFailedException e){
            e.printStackTrace();
        }
    }
}