package controllers.interfaces;

import models.Hotel;
import models.User;
import java.util.List;

public interface IHotelController {
    List<Hotel> getHotelsByCity(String city, User currentUser);

    void showAllHotels();  // ✅ Теперь интерфейс знает о методе showAllHotels()
}
