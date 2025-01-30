package controllers;

import repositories.interfaces.IRoomRepository;

import java.util.Date;

public class RoomController {
    private final IRoomRepository roomRepository;

    public RoomController(IRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public boolean isRoomAvailable(int roomId, Date checkIn, Date checkOut) {
        return roomRepository.isRoomAvailable(roomId, checkIn, checkOut);
    }
}
