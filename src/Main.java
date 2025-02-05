import controllers.*;
import controllers.interfaces.*;
import data.PostgresDB;
import data.interfaces.IDB;
import main.HotelBookingApplication;
import repositories.*;
import repositories.interfaces.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {
        try {
            // Подключение к базе данных
            IDB db = PostgresDB.getInstance("localhost", 5432, "HotelProject", "postgres", "12060745");

            if (db.getConnection() == null) {
                System.err.println("❌ Ошибка: Не удалось подключиться к базе данных.");
                return;
            }

            // Проверяем существование таблиц
            if (!checkTableExists(db, "orders") || !checkTableExists(db, "users")) {
                System.err.println("❌ Ошибка: Отсутствуют необходимые таблицы.");
                return;
            }

            // ✅ Используем Factory для репозиториев
            RepositoryFactory factory = new RepositoryFactory(db);

            IHotelRepository hotelRepo = factory.createHotelRepository();
            IRoomRepository roomRepo = factory.createRoomRepository();
            IBookingRepository bookingRepo = factory.createBookingRepository();
            IUserRepository userRepo = factory.createUserRepository();

            // ✅ Используем созданные репозитории для контроллеров
            IHotelController hotelController = new HotelController(hotelRepo, roomRepo);
            IRoomController roomController = new RoomController(roomRepo);
            IBookingController bookingController = new BookingController(bookingRepo, userRepo, roomRepo, hotelRepo);
            IUserController userController = new UserController(userRepo);

            // Проверяем и создаем администратора и тестового пользователя (если нужно)
            ensureAdminExists(userRepo);
            ensureTestUserExists(userRepo);

            // Запуск главного меню
            HotelBookingApplication app = new HotelBookingApplication(
                    hotelController,
                    roomController,
                    bookingController,
                    userController
            );
            app.mainMenu();

            // Закрытие соединения
            db.close();
        } catch (Exception e) {
            System.err.println("❌ Ошибка в работе приложения: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Метод для проверки существования таблицы
    private static boolean checkTableExists(IDB db, String tableName) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement(
                     "SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = ?);")) {
            stmt.setString(1, tableName.toLowerCase());
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getBoolean(1);
        } catch (Exception e) {
            System.err.println("❌ Ошибка при проверке таблицы: " + e.getMessage());
            return false;
        }
    }

    // Метод для проверки и создания администратора
    private static void ensureAdminExists(IUserRepository userRepo) {
        try (Connection con = userRepo.getDb().getConnection();
             PreparedStatement stmt = con.prepareStatement(
                     "SELECT COUNT(*) FROM users WHERE email = 'admin@mail.com'")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                try (PreparedStatement insertStmt = con.prepareStatement(
                        "INSERT INTO users (name, email, age, password, role) VALUES (?, ?, ?, ?, ?)")) {
                    insertStmt.setString(1, "Admin");
                    insertStmt.setString(2, "admin@mail.com");
                    insertStmt.setInt(3, 30);
                    insertStmt.setString(4, "adminPass");
                    insertStmt.setString(5, "ADMIN");
                    insertStmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка при создании администратора: " + e.getMessage());
        }
    }

    // Метод для проверки и создания тестового пользователя
    private static void ensureTestUserExists(IUserRepository userRepo) {
        try (Connection con = userRepo.getDb().getConnection();
             PreparedStatement stmt = con.prepareStatement(
                     "SELECT COUNT(*) FROM users WHERE email = 'testuser@mail.com'")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                try (PreparedStatement insertStmt = con.prepareStatement(
                        "INSERT INTO users (name, email, age, password, role) VALUES (?, ?, ?, ?, ?)")) {
                    insertStmt.setString(1, "Test User");
                    insertStmt.setString(2, "testuser@mail.com");
                    insertStmt.setInt(3, 25);
                    insertStmt.setString(4, "testPass");
                    insertStmt.setString(5, "USER");
                    insertStmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка при создании Test User: " + e.getMessage());
        }
    }
}
