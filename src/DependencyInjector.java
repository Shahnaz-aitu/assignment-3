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
import repositories.BookingRepository;
import repositories.HotelRepository;
import repositories.RoomRepository;
import repositories.UserRepository;
import repositories.interfaces.IBookingRepository;
import repositories.interfaces.IHotelRepository;
import repositories.interfaces.IRoomRepository;
import repositories.interfaces.IUserRepository;

public class DependencyInjector {
    public static HotelBookingApplication createApplication() throws Exception {
        // Создаем подключение к базе данных
        IDB db = new PostgresDB("localhost", 5432, "HotelProject", "postgres", "password123");

        // Проверяем соединение с базой данных
        if (db.getConnection() == null) {
            throw new IllegalStateException("Ошибка: Не удалось установить соединение с базой данных.");
        }

        // Создаем репозитории
        IHotelRepository hotelRepo = new HotelRepository(db);
        IRoomRepository roomRepo = new RoomRepository(db);
        IBookingRepository bookingRepo = new BookingRepository(db);
        IUserRepository userRepo = new UserRepository(db);

        // Создаем контроллеры
        IHotelController hotelController = new HotelController(hotelRepo);
        IRoomController roomController = new RoomController(roomRepo);
        IBookingController bookingController = new BookingController(bookingRepo, userRepo, roomRepo);
        IUserController userController = new UserController(userRepo);

        // Возвращаем экземпляр приложения
        return new HotelBookingApplication(
                hotelController,
                roomController,
                bookingController,
                userController
        );
    }
}