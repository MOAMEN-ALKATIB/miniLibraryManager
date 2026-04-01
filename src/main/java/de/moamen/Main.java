package de.moamen;

import de.moamen.controller.Cli;
import de.moamen.database.BookToDatabaseConnector;
import de.moamen.database.BookToDatabaseConnectorImpl;
import de.moamen.database.DataSource;
import de.moamen.database.DataSourceImpl;
import de.moamen.model.Author;
import de.moamen.model.Book;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Cli.cli();
    }
}