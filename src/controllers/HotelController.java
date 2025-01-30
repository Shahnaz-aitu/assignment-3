package controllers;

import controllers.interfaces.IHotelController;
import models.Hotel;
import repositories.interfaces.IHotelRepository;

import java.util.List;

public class HotelController implements IHotelController {
    private final IHotelRepository hotelRepository;

    public HotelController(IHotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public HotelController(IHotelRepository hotelRepo) {
    }

    @Override
    public List<Hotel> getHotelsByCity(String city) {
        return hotelRepository.getHotelsByCity(city);
    }
}
