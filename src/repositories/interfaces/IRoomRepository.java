package repositories.interfaces;

import models.Room;
import java.util.List;

public interface IRoomRepository {
    List<Room> getRoomsByHotelId(int hotelId);
}
