package controllers.interfaces; // ✅ Должен быть этот пакет

import models.Room;

import java.util.Date;
import java.util.List;

public interface IRoomController {
    List<Room> getRoomsByHotelId(int hotelId); // ✅ Метод получения списка номеров по ID отеля
    boolean checkRoomAvailability(int roomId, Date checkIN, Date checkOut);
}
