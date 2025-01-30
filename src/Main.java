import controllers.HotelController;
import controllers.RoomController;
import controllers.BookingController;
import controllers.interfaces.IHotelController;
import controllers.interfaces.IRoomController;
import controllers.interfaces.IBookingController;
import data.PostgresDB;
import data.interfaceces.IDB;
import repositories.HotelRepository;
import repositories.RoomRepository;
import repositories.BookingRepository;
import repositories.interfaces.IHotelRepository;
import repositories.interfaces.IRoomRepository;
import repositories.interfaces.IBookingRepository;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "0000", "hotel_booking");

        IHotelRepository hotelRepo = new HotelRepository(db);
        IRoomRepository roomRepo = new RoomRepository(db);
        IBookingRepository bookingRepo = new BookingRepository(db);

        IHotelController hotelController = new HotelController(hotelRepo);
        IRoomController roomController = new RoomController(roomRepo);
        IBookingController bookingController = new BookingController(bookingRepo);

        HotelBookingApplication app = new HotelBookingApplication(hotelController, roomController, bookingController);
        app.mainMenu();

        db.close();
    }
}
