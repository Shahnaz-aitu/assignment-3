import controllers.HotelController;
import controllers.RoomController;
import controllers.BookingController;
import data.PostgresDB;
import data.interfaces.IDB;
import repositories.BookingRepository;
import repositories.HotelRepository;
import repositories.RoomRepository;
import repositories.interfaces.IHotelRepository;
import repositories.interfaces.IRoomRepository;
import repositories.interfaces.IBookingRepository;

public class Main {
    public static <IRoomRepository> void main(String[] args) {
        // Создаём соединение с базой данных
        IDB db = new PostgresDB();

        // Создаём репозитории
        IHotelRepository hotelRepo = new HotelRepository(db);
        IRoomRepository roomRepo = new RoomRepository(db);
        IBookingRepository bookingRepo = new BookingRepository(db);

        // Создаём контроллеры
        HotelController hotelController = new HotelController(hotelRepo);
        RoomController roomController = new RoomController(roomRepo);
        BookingController bookingController = new BookingController(bookingRepo);

        // Запускаем приложение
        HotelBookingApplication app = new HotelBookingApplication(hotelController, roomController, bookingController);
        app.mainMenu();

        // Закрываем соединение с БД
        db.close();
    }
}
