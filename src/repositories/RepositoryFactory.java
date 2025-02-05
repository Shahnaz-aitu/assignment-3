package repositories;

import data.interfaces.IDB;
import repositories.interfaces.*;

public class RepositoryFactory {
    private final IDB db;

    public RepositoryFactory(IDB db) {
        this.db = db;
    }

    public IUserRepository createUserRepository() {
        return new UserRepository(db);
    }

    public IHotelRepository createHotelRepository() {
        return new HotelRepository(db);
    }

    public IRoomRepository createRoomRepository() {
        return new RoomRepository(db);
    }

    public IBookingRepository createBookingRepository() {
        return new BookingRepository(db);
    }
}
