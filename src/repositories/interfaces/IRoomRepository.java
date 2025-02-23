package repositories.interfaces;

import models.Room;
import java.util.Date;
import java.util.List;

public interface IRoomRepository {
    List<Room> getRoomsByHotelId(int hotelId);
    boolean isRoomAvailable(int roomId, Date checkIn, Date checkOut);
    Room getRoomById(int id);

    // Добавляем метод getAllRooms()
    List<Room> getAllRooms();
}
