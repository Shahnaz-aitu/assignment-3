package controllers;

import controllers.interfaces.IRoomController;
import repositories.interfaces.IRoomRepository;

import java.util.Date;

public class RoomController implements IRoomController {
    private final IRoomRepository roomRepository;

    public RoomController(IRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public boolean isRoomAvailable(int roomId, Date checkIn, Date checkOut) {
        return roomRepository.isRoomAvailable(roomId, checkIn, checkOut);
    }
}
