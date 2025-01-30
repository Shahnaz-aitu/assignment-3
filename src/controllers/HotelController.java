package controllers;

import controllers.interfaces.IHotelController;
import repositories.interfaces.IHotelRepository;
import models.Hotel;
import java.util.List;

public class HotelController implements IHotelController {
    private final IHotelRepository hotelRepository;

    public HotelController(IHotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Hotel> getHotelsByCity(String city) {
        return hotelRepository.getHotelsByCity(city);
    }
}
