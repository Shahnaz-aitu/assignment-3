package controllers.interfaces;

import models.Room;
import models.User;
import java.util.List;

public interface IRoomController {
    List<Room> getRoomsByHotelId(int hotelId, User currentUser);
}
