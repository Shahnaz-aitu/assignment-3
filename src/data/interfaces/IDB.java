package data.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDB {
    //interface with method Connection
    Connection getConnection() throws SQLException;
    void close();
}