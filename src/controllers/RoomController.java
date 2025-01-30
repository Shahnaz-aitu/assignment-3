package controllers;

import controllers.interfaces.IRoomController;
import models.Room;
import repositories.interfaces.IRoomRepository;
import java.util.List;

public class RoomController implements IRoomController {
    private final IRoomRepository roomRepository;

    public RoomController(IRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getRoomsByHotelId(int hotelId) {
        return roomRepository.getRoomsByHotelId(hotelId);
    }
}
