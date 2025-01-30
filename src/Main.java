import controllers.HotelController;
import controllers.RoomController;
import controllers.BookingController;
import controllers.interfaces.IHotelController;
import controllers.interfaces.IRoomController;
import controllers.interfaces.IBookingController;
import data.PostgresDB;
import data.interfaces.IDB; // Исправлена опечатка в имени пакета (interfaceces → interfaces)
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
        // Используем try-with-resources, если PostgresDB реализует AutoCloseable
        try (IDB db = new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "0000", "hotel_booking")) {
            // Инициализация репозиториев
            IHotelRepository hotelRepo = new HotelRepository(db);
            IRoomRepository roomRepo = new RoomRepository(db);
            IBookingRepository bookingRepo = new BookingRepository(db);
            IUserRepository userRepo = new UserRepository(db);

            // Создание контроллеров с зависимостями
            IHotelController hotelController = new HotelController(hotelRepo);
            IRoomController roomController = new RoomController(roomRepo);
            IBookingController bookingController = new BookingController(
                    bookingRepo,
                    userRepo,
                    roomRepo // BookingController требует IRoomRepository
            );

            // Запуск приложения
            HotelBookingApplication app = new HotelBookingApplication(
                    hotelController,
                    roomController,
                    bookingController
            );
            app.mainMenu(); // Исправлено: start() → mainMenu()

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}