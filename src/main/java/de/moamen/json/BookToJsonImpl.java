package de.moamen.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.moamen.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookToJsonImpl implements BookToJson {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(BookToJsonImpl.class);
    private static final String BASIS_FILE = new File("").getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "library.json";

    @Override
    public void saveBookAsJsonInFile(Book book) {
        logger.debug("Attempting to save a book as Json in the file {}", book);
        List<Book> bookList = loadBooksAsJsonFromFile();
        bookList.add(book);
        try (Writer writer = new FileWriter(BASIS_FILE)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, bookList);
            logger.info("Book saved as JSON: {}", book);
        } catch (IOException e) {
            logger.error("Error writing JSON to file: {}", e.getMessage());
        }
    }

    @Override
    public List<Book> loadBooksAsJsonFromFile() {
        logger.debug("Attempting to load books from the json-file");
        try (Reader reader = new FileReader(BASIS_FILE)) {
            List<Book> bookList = objectMapper.readValue(reader, new TypeReference<>() {
            });
            logger.info("{} loaded from the json-file", bookList.size());
            return bookList;
        } catch (IOException e) {
            logger.error("error during reading the json from the File {}", e.getMessage());
            return new ArrayList<>();
        }
    }
}