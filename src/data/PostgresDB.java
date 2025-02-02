package data;

import data.interfaces.IDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    private final String url;
    private final String user;
    private final String password;
    private Connection connection;

    public PostgresDB(String host, int port, String database, String user, String password) {
        this.url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
        this.user = user;
        this.password = password;
        try {
            // Регистрируем драйвер PostgreSQL
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Не удалось загрузить драйвер PostgreSQL.");
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}