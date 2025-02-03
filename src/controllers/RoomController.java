package controllers;

import controllers.interfaces.IRoomController;
import models.Room;
import repositories.interfaces.IRoomRepository;

import java.util.Date;
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

    @Override
    public boolean checkRoomAvailability(int roomId, Date checkIn, Date checkOut) {
        try {
            boolean available = roomRepo.isRoomAvailable(roomId, checkIn, checkOut);
            if (available) {
                System.out.println("Номер с ID " + roomId + " доступен с " + checkIn + " по " + checkOut);
            } else {
                System.out.println("Номер с ID " + roomId + " занят на даты с " + checkIn + " по " + checkOut);
            }
            return available;
        } catch (Exception e) {
            System.err.println("Ошибка проверки доступности номера: " + e.getMessage());
            return false;
        }
    }
}
