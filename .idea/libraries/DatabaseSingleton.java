public class Database {
    private static Database instance; // Единственный экземпляр объекта
    private static final String URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    private Database() {
        // Приватный конструктор предотвращает создание экземпляра извне
    }

    // Метод для получения объекта Database (только один экземпляр)
    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}