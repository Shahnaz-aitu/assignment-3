package data;

import data.interfaceces.IDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    private String host;
    private String username; //postgres username
    private String password; //postgres password
    private String dbName; //postgres database

    private Connection connection;

    public PostgresDB(String host, String username, String password, String dbName) {
        setHost(host);
        setUsername(username);
        setPassword(password);
        setDbName(dbName);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public Connection getConnection() {
        String connectionUrl = host + "/" + dbName;
        try {
            if(connection != null && !connection.isClosed()) {

                return connection;
            }
            Class.forName("org.postgresql.Driver"); //check JDBC driver existence
            connection = DriverManager.getConnection(connectionUrl, username, password);
            return connection;
        }catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void close() {
        if(connection != null) {
            try{
                connection.close();
            }catch (SQLException e) {
                System.out.println("Failed to close connection" + e.getMessage());
            }
        }
    }
}
