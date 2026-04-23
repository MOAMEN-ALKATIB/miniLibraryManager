package de.moamen.service;

import de.moamen.model.Author;
import de.moamen.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {
    /*
    Library library;
    @BeforeEach
    void setUp(){
        library=new Library();
    }
    */
    static Stream<Book> bookProvider() {
        return Stream.of(
                new Book(1, "Java", 2020, new Author("Moamen")),
                new Book(2, "Spring", 2021, new Author("Ali"))
        );
    }

    @ParameterizedTest
    @MethodSource("bookProvider")
    void testAddBook(Book input) {
        Library library = new Library();
        library.addBook(input);

        List<Book> result = library.getBookList();
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteBook() {
        Library library = new Library();
        library.getBookList().add(new Book(1, "java", 2020, new Author("moamen")));

        library.deleteBook(1);
        List<Book> result = library.getBookList();
        assertEquals(0, result.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testFindBookByISBN(int input) {
        Library library = new Library();

        Book book = new Book(1, "java", 2020, new Author("moamen"));
        library.getBookList().add(book);

        Book result = library.findBook(input);
        if (input==1){
            assertNotNull(result);
        }else{
            assertNull(result);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"java", "JAVA", "JaVa", "C++"})
    void testFindBookByTitleIgnoreCase(String input) {
        Library library = new Library();

        library.addBook(new Book(1, "Java", 2020, new Author("Moamen")));

        Book result = library.findBook(input);

        if (input.equalsIgnoreCase("java")) {
            assertNotNull(result);
        } else {
            assertNull(result);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ali", "Moamen"})
    void testFindBooksByAuthor(String input) {
        Library library = new Library();

        library.getBookList().add(new Book(1, "Java", 2020, new Author("Ali")));
        library.getBookList().add(new Book(2, "Spring", 2021, new Author("Ali")));
        library.getBookList().add(new Book(3, "C++", 2022, new Author("Moamen")));

        List<Book> result = library.findBooksByAuthor(input);

        if (input.equals("Ali")) {
            assertEquals(2, result.size());
        } else {
            assertEquals(1, result.size());
        }
    }
}
