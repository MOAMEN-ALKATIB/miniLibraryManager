package de.moamen.web;

import de.moamen.model.Book;
import de.moamen.service.LibraryService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LibraryController {
    private final LibraryService libraryService = new LibraryService();

    @POST
    @Path("/insert")
    public void insertBook(Book book) {
        libraryService.insertBook(book);
    }

    @DELETE
    @Path("/{isbn}")
    public Book deleteBook(@PathParam("isbn") int isbn) {
        return libraryService.deleteBook(isbn);
    }

    @GET
    @Path("/{isbn}")
    public Book findBookByISBN(@PathParam("isbn") int isbn) {
        return libraryService.findBookByISBN(isbn);
    }

    @GET
    @Path("/search")
    public Book findBookByTitle(@QueryParam("title") String title) {
        return libraryService.findBookByTitle(title);
    }

    @GET
    @Path("/author")
    public List<Book> findBooksByAuthor(@QueryParam("authorName") String authorName) {
        return libraryService.findBooksByAuthor(authorName);
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> displayBooks() {
        return libraryService.displayBooks();
    }

    @GET
    @Path("/sort/year")
    public List<Book> sortBooksByYear() {
        return libraryService.sortBooksByYear();
    }

    @GET
    @Path("/sort/title")
    public List<Book> sortBooksByTitle() {
        return libraryService.sortBooksByTitle();
    }
}