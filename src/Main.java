import controllers.BookingController;
import controllers.HotelController;
import controllers.RoomController;
import controllers.UserController;
import controllers.interfaces.IBookingController;
import controllers.interfaces.IHotelController;
import controllers.interfaces.IRoomController;
import controllers.interfaces.IUserController;
import data.PostgresDB;
import data.interfaces.IDB;
import models.User;
import repositories.BookingRepository;
import repositories.HotelRepository;
import repositories.RoomRepository;
import repositories.UserRepository;
import repositories.interfaces.IBookingRepository;
import repositories.interfaces.IHotelRepository;
import repositories.interfaces.IRoomRepository;
import repositories.interfaces.IUserRepository;

public class Main {
    public static void main(String[] args) {
        System.out.println("Запуск Hotel Booking System...");

        try {
            // Создаем подключение к базе данных
            IDB db = new PostgresDB("localhost", 5432, "HotelProject", "postgres", "12060745");

            if (db.getConnection() == null) {
                System.err.println("Ошибка: Не удалось установить соединение с базой данных.");
                return;
            }

            // Инициализация репозиториев
            IHotelRepository hotelRepo = new HotelRepository(db);
            IRoomRepository roomRepo = new RoomRepository(db);
            IBookingRepository bookingRepo = new BookingRepository(db);
            IUserRepository userRepo = new UserRepository(db);

            // Создание контроллеров с зависимостями
            IHotelController hotelController = new HotelController(hotelRepo);
            IRoomController roomController = new RoomController(roomRepo);
            // Передаем все 4 аргумента в BookingController
            IBookingController bookingController = new BookingController(bookingRepo, userRepo, roomRepo, hotelRepo);
            IUserController userController = new UserController(userRepo);

            // Запуск приложения
            HotelBookingApplication app = new HotelBookingApplication(
                    hotelController,
                    roomController,
                    bookingController,
                    userController
            );

            app.mainMenu();

            // Закрываем соединение после завершения работы приложения
            db.close();
            System.out.println("Приложение завершено.");
        } catch (Exception e) {
            System.err.println("Ошибка во время работы приложения: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
