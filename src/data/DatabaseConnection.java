package data;

public class DatabaseConnection {
    private static volatile DatabaseConnection instance;

    private DatabaseConnection() {
        // Приватный конструктор
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    // Дополнительные методы для управления соединением можно добавить здесь
}
