package de.moamen.database;

import de.moamen.model.Author;
import de.moamen.model.Book;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSourceImpl implements DataSource {
    private static final String DN_NAME = "library.db";
    private static final String CONNECTIONS_STRING = "jdbc:sqlite:" + new File("").getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + DN_NAME;

    private static final String TABLE_NAME = "BOOK";
    private static final String COLUMN_ISBN = "isbn";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_AUTHOR_NAME = "authorName";

    private static final String INSERT_BOOK = "insert into " + TABLE_NAME + "(" + COLUMN_ISBN + "," + COLUMN_TITLE + "," + COLUMN_YEAR + "," + COLUMN_AUTHOR_NAME + ") values(?,?,?,?)";
    private static final String FIND_BOOK = "select * from " + TABLE_NAME + " where isbn=?";


    private Connection connection;
    private PreparedStatement insertBook;
    private PreparedStatement findBook;

    @Override
    public boolean open() {
        try {
            connection = DriverManager.getConnection(CONNECTIONS_STRING);
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
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
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
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> bookQuery() {
        List<Book> bookList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from " + TABLE_NAME);
            while (resultSet.next()) {
                int isbn = resultSet.getInt(COLUMN_ISBN);
                String title = resultSet.getString(COLUMN_TITLE);
                int year = resultSet.getInt(COLUMN_YEAR);
                String authorName = resultSet.getString(COLUMN_AUTHOR_NAME);
                bookList.add(new Book(isbn, title, year, new Author(authorName)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book findBook(int isbn) {
        try {
            findBook.setInt(1, isbn);
            ResultSet resultSet = findBook.executeQuery();
            if (resultSet.next()) {
                int is = resultSet.getInt(COLUMN_ISBN);
                String title = resultSet.getString(COLUMN_TITLE);
                int year = resultSet.getInt(COLUMN_YEAR);
                String authorName = resultSet.getString(COLUMN_AUTHOR_NAME);
                return new Book(is, title, year, new Author(authorName));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
