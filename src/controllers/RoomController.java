package controllers;

import controllers.interfaces.IRoomController;
import models.Permission;
import models.Room;
import models.User;
import repositories.interfaces.IRoomRepository;

import java.util.ArrayList;
import java.util.List;

public class RoomController implements IRoomController {
    private final IRoomRepository roomRepository;

    public RoomController(IRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getRoomsByHotelId(int hotelId, User currentUser) {
        if (!currentUser.hasPermission(Permission.VIEW_ROOMS)) {  // Исправлено
            System.out.println("Ошибка: недостаточно прав для просмотра номеров");
            return new ArrayList<>();
        }
        return roomRepository.getRoomsByHotelId(hotelId);
    }
}
