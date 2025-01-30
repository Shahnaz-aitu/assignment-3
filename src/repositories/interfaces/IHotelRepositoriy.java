package repositories.interfaces;

import models.Hotel;
import java.util.List;

public interface IHotelRepository {
    boolean createHotel(Hotel hotel);
    Hotel getHotelById(int id);
    List<Hotel> getAllHotels();
    boolean updateHotel(Hotel hotel);
    boolean deleteHotel(int id);
}