import controllers.*;
import controllers.interfaces.*;
import data.PostgresDB;
import data.interfaces.IDB;
import main.HotelBookingApplication;
import models.*;
import repositories.*;
import repositories.interfaces.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 Запуск Hotel Booking System...");

        try {
            // Подключение к базе данных
            IDB db = new PostgresDB("localhost", 5432, "HotelProject", "postgres", "12060745");

            if (db.getConnection() == null) {
                System.err.println("❌ Ошибка: Не удалось установить соединение с базой данных.");
                return;
            }

            // Проверяем существование таблиц
            if (!checkTableExists(db, "orders") || !checkTableExists(db, "users")) {
                System.err.println("❌ Ошибка: Одна из таблиц (orders/users) не найдена. Проверьте, правильно ли названы таблицы.");
                return;
            }

            // Инициализация репозиториев
            IHotelRepository hotelRepo = new HotelRepository(db);
            IRoomRepository roomRepo = new RoomRepository(db);
            IBookingRepository bookingRepo = new BookingRepository(db);
            IUserRepository userRepo = new UserRepository(db);

            // Создание контроллеров
            IHotelController hotelController = new HotelController(hotelRepo);
            IRoomController roomController = new RoomController(roomRepo);
            IBookingController bookingController = new BookingController(bookingRepo, userRepo, roomRepo, hotelRepo);
            IUserController userController = new UserController(userRepo);

            // Проверяем, есть ли администратор, если нет - создаем
            ensureAdminExists(userRepo);

            // Проверяем, есть ли тестовый пользователь, если нет - создаем
            ensureTestUserExists(userRepo);

            // 📌 Тест 1: Проверка JOIN-запроса (получение деталей заказа)
            System.out.println("\n=== ✅ Проверка JOIN-запроса (получение деталей заказа) ===");
            Order order = Order.getFullOrderDescription(1, db);
            if (order != null) {
                System.out.println("Заказ №" + order.getOrderId());
                System.out.println("Клиент: " + order.getUserName() + " (" + order.getUserEmail() + ")");
                System.out.println("Товары:");
                for (OrderDetails details : order.getOrderDetails()) {
                    System.out.println("- " + details.getProductName() + " (Категория: " + details.getCategory() + ")");
                }
            } else {
                System.out.println("⚠️ Ошибка при получении заказа.");
            }

            // 📌 Тест 2: Проверка удаления пользователя (только ADMIN)
            System.out.println("\n=== ✅ Проверка удаления пользователя (роль ADMIN) ===");
            User adminUser = userRepo.getUserByEmail("admin@mail.com");

            if (adminUser == null) {
                System.err.println("❌ Ошибка: Не удалось загрузить администратора из базы.");
            } else {
                System.out.println("Роль текущего пользователя: " + adminUser.getRole());
                if (adminUser.getRole() == Role.ADMIN) {
                    boolean deleted = userController.deleteUser("testuser@mail.com", adminUser);
                    System.out.println("Удаление пользователя: " + (deleted ? "✅ Успешно" : "❌ Ошибка (возможно, у вас нет прав)"));
                } else {
                    System.err.println("❌ Ошибка: Пользователь не является ADMIN.");
                }
            }

            // 📌 Тест 3: Проверка полного описания бронирования
            System.out.println("\n=== ✅ Проверка полной информации о бронировании ===");
            int bookingId = 1; // Укажи нужный ID бронирования
            bookingController.showFullBookingDescription(bookingId);

            // Запуск основного меню приложения
            HotelBookingApplication app = new HotelBookingApplication(
                    hotelController,
                    roomController,
                    bookingController,
                    userController
            );
            app.mainMenu();

            // Закрытие соединения
            db.close();
            System.out.println("✅ Приложение завершено.");
        } catch (Exception e) {
            System.err.println("❌ Ошибка во время работы приложения: " + e.getMessage());
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
                System.out.println("ℹ️ Администратор не найден. Создаем нового администратора...");

                try (PreparedStatement insertStmt = con.prepareStatement(
                        "INSERT INTO users (name, email, age, password, role) VALUES (?, ?, ?, ?, ?)")) {
                    insertStmt.setString(1, "Admin");
                    insertStmt.setString(2, "admin@mail.com");
                    insertStmt.setInt(3, 30);
                    insertStmt.setString(4, "adminPass");
                    insertStmt.setString(5, "ADMIN");
                    insertStmt.executeUpdate();
                    System.out.println("✅ Администратор успешно создан!");
                }
            } else {
                System.out.println("✅ Администратор уже существует.");
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
                System.out.println("ℹ️ Test User не найден. Создаем тестового пользователя...");

                try (PreparedStatement insertStmt = con.prepareStatement(
                        "INSERT INTO users (name, email, age, password, role) VALUES (?, ?, ?, ?, ?)")) {
                    insertStmt.setString(1, "Test User");
                    insertStmt.setString(2, "testuser@mail.com");
                    insertStmt.setInt(3, 25);
                    insertStmt.setString(4, "testPass");
                    insertStmt.setString(5, "USER");
                    insertStmt.executeUpdate();
                    System.out.println("✅ Test User успешно создан!");
                }
            } else {
                System.out.println("✅ Test User уже существует.");
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка при создании Test User: " + e.getMessage());
        }
    }
}
