package controllers.interfaces;

import models.Hotel;
import java.util.List;

public interface IHotelController {
    List<Hotel> getHotelsByCity(String city);
}
