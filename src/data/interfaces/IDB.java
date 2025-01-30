package data.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDB {
    Connection getConnection() throws SQLException;
    // Метод close() уже существует в AutoCloseable
    void close();
}