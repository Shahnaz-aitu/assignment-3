package controllers.interfaces;

import models.Hotel;
import models.Room;
import models.User;
import java.util.List;

public interface IHotelController {
    List<Hotel> getHotelsByCity(String city, User currentUser);

    void showAllHotels();

    List<Room> getRoomsByHotelId(int hotelId, User user); // ✅ Новый метод!
}
