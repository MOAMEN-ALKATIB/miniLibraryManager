package de.moamen.database;

import de.moamen.model.Author;
import de.moamen.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSourceImpl implements DataSource {
    private static final String TABLE_NAME = "BOOK";
    private static final String COLUMN_ISBN = "isbn";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_AUTHOR_NAME = "authorName";

    private static final String INSERT_BOOK = "insert into " + TABLE_NAME + "(" + COLUMN_ISBN + "," + COLUMN_TITLE + "," + COLUMN_YEAR + "," + COLUMN_AUTHOR_NAME + ") values(?,?,?,?)";
    private static final String FIND_BOOK = "select * from " + TABLE_NAME + " where isbn=?";
    private static final String DELETE_BOOK = "delete from " + TABLE_NAME + " where isbn=?";


    private Connection connection;
    private PreparedStatement insertBook;
    private PreparedStatement findBook;
    private PreparedStatement deleteBook;

    private static final Logger logger= LoggerFactory.getLogger(DataSourceImpl.class);

    @Override
    public boolean open() {
        File dbFile= new File(System.getProperty("user.home")
                + File.separator + "miniLibrary"
                + File.separator + "library.db");
        dbFile.getParentFile().mkdirs();
        final String CONNECTIONS_STRING = "jdbc:sqlite:" + dbFile.getAbsolutePath();
        try {
            logger.info("Opening database connection");
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(CONNECTIONS_STRING);
            logger.info("Database connection opened");
            logger.debug("Ensuring BOOK table exists");
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(
                        "CREATE TABLE IF NOT EXISTS BOOK (" +
                                "isbn INTEGER PRIMARY KEY," +
                                "title TEXT," +
                                "year INTEGER," +
                                "authorName TEXT" +
                                ")"
                );
            }
            insertBook = connection.prepareStatement(INSERT_BOOK);
            findBook = connection.prepareStatement(FIND_BOOK);
            deleteBook= connection.prepareStatement(DELETE_BOOK);
            return true;
        } catch (SQLException |ClassNotFoundException e) {
            logger.error("Error opening database",e);
            return false;
        }
    }

    @Override
    public void close() {
        try {
            if (insertBook != null) {
                insertBook.close();
            }
            if (findBook != null) {
                findBook.close();
            }
            if (deleteBook != null){
                deleteBook.close();
            }
            if (connection != null) {
                connection.close();
                logger.info("Connection closed");
            }
        } catch (SQLException e) {
            logger.error("Error during closing the Connection",e);
        }
    }

    @Override
    public List<Book> bookQuery() {
        List<Book> bookList = new ArrayList<>();
        logger.debug("Fetching all books");
        try (Statement statement = connection.createStatement();ResultSet resultSet = statement.executeQuery("select * from " + TABLE_NAME)) {
            while (resultSet.next()) {
                int isbn = resultSet.getInt(COLUMN_ISBN);
                String title = resultSet.getString(COLUMN_TITLE);
                int year = resultSet.getInt(COLUMN_YEAR);
                String authorName = resultSet.getString(COLUMN_AUTHOR_NAME);
                bookList.add(new Book(isbn, title, year, new Author(authorName)));
            }
            logger.debug("Found {} books", bookList.size());
        } catch (SQLException e) {
            logger.error("Error during fetching the books",e);
        }
        return bookList;
    }

    @Override
    public void insertBook(Book book) {
        try {
            insertBook.setInt(1, book.getIsbn());
            insertBook.setString(2, book.getTitle());
            insertBook.setInt(3, book.getYear());
            insertBook.setString(4, book.getAuthor().getName());
            insertBook.executeUpdate();
            logger.info("Book inserted: {} (isbn={})", book.getTitle(), book.getIsbn());
        } catch (SQLException e) {
            logger.error("Error inserting book", e);
        }
    }
    @Override
    public void deleteBook(int isbn) {
        try {
            deleteBook.setInt(1,isbn);
            deleteBook.executeUpdate();
            logger.info("Book deleted successfully");
        }catch (SQLException e){
            logger.error("Error deleting book",e);
        }
    }

    @Override
    public Book findBook(int isbn) {
        try {
            logger.info("Searching book with isbn {}", isbn);
            findBook.setInt(1, isbn);
            try(ResultSet resultSet = findBook.executeQuery()) {
                if (resultSet.next()) {
                    logger.info("Book with isbn {} found",isbn);
                    return new Book(
                            resultSet.getInt(COLUMN_ISBN),
                            resultSet.getString(COLUMN_TITLE),
                            resultSet.getInt(COLUMN_YEAR),
                            new Author(resultSet.getString(COLUMN_AUTHOR_NAME))
                    );
                }
            }
            logger.warn("Book with isbn {} not found",isbn);
        } catch (SQLException e) {
            logger.error("Error searching book",e);
            return null;
        }
        return null;
    }

}
