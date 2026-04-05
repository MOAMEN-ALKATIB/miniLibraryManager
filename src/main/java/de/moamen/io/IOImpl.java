package de.moamen.io;

import de.moamen.exceptions.LoadingFailedException;
import de.moamen.exceptions.NoBookFoundException;
import de.moamen.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOImpl implements IOInterface {
    public static final String BASIS_DIR = new File("").getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "BooksAsFiles";
    private static final Logger logger = LoggerFactory.getLogger(IOImpl.class);

    @Override
    public void save(Book book) {
        File dir = new File(BASIS_DIR);

        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                logger.info("Directory '{}' created for loading books.", BASIS_DIR);
            } else {
                logger.warn("Failed to create directory '{}'. Check permissions.", BASIS_DIR);
            }
        }

        String filePath = BASIS_DIR + File.separator + book.getTitle() + ".ser";
        File file = new File(filePath);
        if (file.exists()) {
            logger.warn("Book '{}' already exists at '{}'. Overwriting...", book.getTitle(), filePath);
        }

        try (FileOutputStream outputStream = new FileOutputStream(file); ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(book);
            logger.info("Book '{}' successfully saved at '{}'", book.getTitle(), filePath);
        } catch (IOException e) {
            logger.error("Failed to save book '{}' at '{}'", book.getTitle(), filePath, e);
        }
    }

    @Override
    public List<Book> load() throws NoBookFoundException, LoadingFailedException {
        List<Book> bookList = new ArrayList<>();
        File dir = new File(BASIS_DIR);

        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                logger.info("Directory '{}' created for loading books.", BASIS_DIR);
            } else {
                logger.warn("Failed to create directory '{}'. Check permissions.", BASIS_DIR);
            }
        }

        File[] files = dir.listFiles(file -> file.getName().endsWith(".ser"));
        if (files != null && files.length == 0) {
            logger.warn("No serialized books found in '{}'", BASIS_DIR);
            throw new NoBookFoundException("There are no saved books");
        }

        if (files != null) {
            for (File file : files) {
                try (FileInputStream inputStream = new FileInputStream(file); ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                    Book book = (Book) objectInputStream.readObject();
                    bookList.add(book);
                    logger.debug("Loaded book '{}' from '{}'", book.getTitle(), file.getAbsolutePath());

                } catch (IOException | ClassNotFoundException exception) {
                    logger.error("Failed to load book from '{}'", file.getAbsolutePath(), exception);
                    throw new LoadingFailedException("loading failed");
                }
            }
        }
        logger.info("Successfully loaded {} books from '{}'", bookList.size(), BASIS_DIR);
        return bookList;
    }
}
