package controllers;

import models.Hotel;
import repositories.interfaces.IHotelRepository;

import java.util.List;

public class HotelController {
    private final IHotelRepository hotelRepository;

    public HotelController(IHotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Hotel> getHotelsByCity(String city) {
        return hotelRepository.getHotelsByCity(city);
    }
}
