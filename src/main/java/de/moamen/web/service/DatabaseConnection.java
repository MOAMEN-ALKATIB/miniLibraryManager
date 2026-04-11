package de.moamen.web.service;

import de.moamen.database.DataSource;
import de.moamen.database.DataSourceImpl;
import de.moamen.model.Book;
import de.moamen.service.Library;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/connection")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DatabaseConnection {
    private final DataSource dataSource = new DataSourceImpl();

    @POST
    @Path("/insertBook")
    public void insertBook(Book book) {
        if (dataSource.open()) {
            dataSource.insertBook(book);
            dataSource.close();
            Library.loadFromDatabase();
        }
    }

    @DELETE
    @Path("/{isbn}")
    public Book deleteBook(@PathParam("isbn") int isbn) {
        Book book = null;
        if (dataSource.open()) {
            book = dataSource.findBook(isbn);
            dataSource.deleteBook(isbn);
            dataSource.close();
            Library.loadFromDatabase();
        }
        return book;
    }

    @GET
    @Path("/{isbn}")
    public Book getBook(@PathParam("isbn") int isbn) {
        if (dataSource.open()) {
            Book book = dataSource.findBook(isbn);
            dataSource.close();
            return book;
        }
        return null;
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> loadBook() {
        if (dataSource.open()) {
            List<Book> books = dataSource.bookQuery();
            dataSource.close();
            return books;
        }
        return List.of();
    }
}
