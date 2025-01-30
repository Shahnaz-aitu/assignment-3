import controllers.HotelController;
import controllers.RoomController;
import controllers.BookingController;
import controllers.UserController;
import controllers.interfaces.IHotelController;
import controllers.interfaces.IRoomController;
import controllers.interfaces.IBookingController;
import controllers.interfaces.IUserController;
import data.PostgresDB;
import data.interfaces.IDB;
import repositories.BookingRepository;
import repositories.HotelRepository;
import repositories.RoomRepository;
import repositories.UserRepository;
import repositories.interfaces.IHotelRepository;
import repositories.interfaces.IRoomRepository;
import repositories.interfaces.IBookingRepository;
import repositories.interfaces.IUserRepository;

public class Main {
    public static void main(String[] args) {
        try {
            // Создаем подключение к базе данных
            IDB db = new PostgresDB("localhost", 5432, "HotelProject", "postgres", "12060745");

            // Инициализация репозиториев
            IHotelRepository hotelRepo = new HotelRepository(db); //  Исправлено
            IRoomRepository roomRepo = new RoomRepository(db);
            IBookingRepository bookingRepo = new BookingRepository(db);
            IUserRepository userRepo = new UserRepository(db); //  Проверь, что UserRepository принимает IDB

            // Создание контроллеров с зависимостями
            IHotelController hotelController = new HotelController(hotelRepo);
            IRoomController roomController = new RoomController(roomRepo);
            IBookingController bookingController = new BookingController(bookingRepo, userRepo, roomRepo);
            IUserController userController = new UserController(userRepo);

            // Запуск приложения
            HotelBookingApplication app = new HotelBookingApplication(
                    hotelController,
                    roomController,
                    bookingController,
                    userController
            );
            app.mainMenu(); // Исправлено

            // Закрываем соединение после завершения работы приложения
            db.close();

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
