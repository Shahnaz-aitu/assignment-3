package controllers;

import controllers.interfaces.IRoomController;
import models.Role;
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
        if (!currentUser.hasPermission(Role.valueOf("VIEW_ROOMS"))) {
            System.out.println("Ошибка: недостаточно прав для просмотра номеров");
            return new ArrayList<>();
        }
        return roomRepository.getRoomsByHotelId(hotelId);
    }
}
