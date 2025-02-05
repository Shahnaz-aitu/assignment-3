package controllers;

import controllers.interfaces.IHotelController;
import models.Hotel;
import models.Permission;
import models.Room;
import models.User;
import repositories.interfaces.IHotelRepository;
import repositories.interfaces.IRoomRepository;

import java.util.ArrayList;
import java.util.List;

public class HotelController implements IHotelController {
    private final IHotelRepository hotelRepository;
    private final IRoomRepository roomRepository;

    public HotelController(IHotelRepository hotelRepository, IRoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Hotel> getHotelsByCity(String city, User currentUser) {
        if (!currentUser.hasPermission(Permission.VIEW_HOTELS)) {
            System.out.println("❌ Ошибка: недостаточно прав для просмотра отелей");
            return new ArrayList<>();
        }
        return hotelRepository.getHotelsByCity(city);
    }

    @Override
    public void showAllHotels() {
        List<Hotel> hotels = hotelRepository.getAllHotels();
        if (hotels.isEmpty()) {
            System.out.println("❌ В базе данных нет отелей.");
        } else {
            System.out.println("\n=== 🏨 Список всех отелей ===");
            for (Hotel hotel : hotels) {
                System.out.println("- " + hotel.getName() + " | Город: " + hotel.getCity() +
                        " | Рейтинг: " + hotel.getRank() + " | Локация: " + hotel.getLocation());
            }
        }
    }

    @Override
    public List<Room> getRoomsByHotelId(int hotelId, User user) {  // ✅ Реализация метода из интерфейса
        if (!user.hasPermission(Permission.VIEW_ROOMS)) {
            System.out.println("❌ Ошибка: недостаточно прав для просмотра номеров");
            return new ArrayList<>();
        }
        return roomRepository.getRoomsByHotelId(hotelId);
    }

    public void showAllRoomsByHotelId(int hotelId, User user) {
        List<Room> rooms = getRoomsByHotelId(hotelId, user);
        if (rooms.isEmpty()) {
            System.out.println("❌ В этом отеле нет доступных номеров.");
            return;
        }

        System.out.println("\n=== 🏨 Доступные номера в отеле ===");
        for (Room room : rooms) {
            System.out.println("- Номер ID: " + room.getId() + " | Тип: " + room.getType() +
                    " | Цена: " + room.getPrice() + " | Доступность: " + (room.isAvailable() ? "✅ Доступен" : "❌ Занят"));
        }
    }
}
