package de.moamen.web.service;

import de.moamen.model.Book;
import de.moamen.service.Library;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Aktion {

    private final Library library=new Library();

    @POST
    @Path("/books")
    public Book createBook(Book book){
        library.addBook(book);
        Library.saveInDatabase();
        return book;
    }

    @GET
    @Path("/{isbn}")
    public Book findBookByIsbn(@QueryParam("isbn") int isbn){
        return library.findBook(isbn);
    }
    @GET
    @Path("/search")
    public Book findBookByTitle(@QueryParam("title") String title){
        return library.findBook(title);
    }

    @GET
    @Path("/author")
    public List<Book> findBooksByAuthor(@QueryParam("authorName") String authorName){
        return library.findBooksByAuthor(authorName);
    }

    @GET
    @Path("/sort/year")
    public void sortBooksByYear(){
        Collections.sort(library.getBookList());
    }

    @GET
    @Path("/sort/title")
    public void sortBooksByTitle(){
        library.getBookList().sort(Comparator.comparing(Book::getTitle));
    }

    @GET
    @Path("/displayBooks")
    public List<Book> displayBooks(){
        return library.getBookList();
    }

}
