package controllers.interfaces;

import models.Room;
import java.util.List;

public interface IRoomController {
    List<Room> getRoomsByHotelId(int hotelId);
}
