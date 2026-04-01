package de.moamen.database;

import de.moamen.model.Book;

import java.util.List;

public class BookToDatabaseConnectorImpl implements BookToDatabaseConnector {
    @Override
    public void saveBook(Book book) {
        DataSource dataSource = new DataSourceImpl();
        if (!dataSource.open()) {
            System.out.println("Error by opening the datasource");
        }
        if (dataSource.findBook(book.getIsbn()) == null) {
            dataSource.insertBook(book);
        }
        dataSource.close();
    }

    @Override
    public List<Book> loadBook() {
        DataSource dataSource = new DataSourceImpl();
        if (!dataSource.open()) {

        }
        List<Book> bookList = dataSource.bookQuery();
        dataSource.close();
        return bookList;
    }
}
