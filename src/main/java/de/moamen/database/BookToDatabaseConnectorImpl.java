package de.moamen.database;

import de.moamen.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BookToDatabaseConnectorImpl implements BookToDatabaseConnector {
    private static final Logger logger=LoggerFactory.getLogger(BookToDatabaseConnectorImpl.class);
    private final DataSource dataSource=new DataSourceImpl();
    @Override
    public void saveBook(Book book) {
        logger.debug("Attempting to save book {}",book);
        try {
            if (!dataSource.open()) {
                logger.error("Error opening datasource");
                return;
            }
            if (dataSource.findBook(book.getIsbn()) == null) {
                dataSource.insertBook(book);
                logger.info("book inserted successfully: {}",book);
            }else {
                logger.warn("Book already exists in database: {}", book);
            }
        }finally {
            dataSource.close();
        }
    }

    @Override
    public List<Book> loadBook() {
        logger.debug("Attempting to load books from database");
        try {
            if (!dataSource.open()) {
                logger.error("Error opening datasource");
                return new ArrayList<>();
            }
            List<Book> bookList = dataSource.bookQuery();
            logger.info("Loaded {} books from database", bookList.size());
            return bookList;
        }finally {
            dataSource.close();
        }
    }
}
