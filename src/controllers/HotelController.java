package controllers;

import controllers.interfaces.IHotelController;
import models.Hotel;
import models.Permission;
import models.User;
import repositories.interfaces.IHotelRepository;

import java.util.ArrayList;
import java.util.List;

public class HotelController implements IHotelController {
    private final IHotelRepository hotelRepository;

    public HotelController(IHotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Hotel> getHotelsByCity(String city, User currentUser) {
        if (!currentUser.hasPermission(Permission.VIEW_HOTELS)) {  // Исправлено
            System.out.println("Ошибка: недостаточно прав для просмотра отелей");
            return new ArrayList<>();
        }
        return hotelRepository.getHotelsByCity(city);
    }
}
