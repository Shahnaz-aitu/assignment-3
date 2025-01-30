package repositories;

import models.Room;
import repositories.interfaces.IRoomRepository;
import data.interfaces.IDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RoomRepository implements IRoomRepository {
    private final IDB db;

    public RoomRepository(IDB db) {
        this.db = db;
    }

    @Override
    public List<Room> getRoomsByHotelId(int hotelId) {
        List<Room> rooms = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM rooms WHERE hotel_id = ?")) {
            stmt.setInt(1, hotelId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getInt("hotel_id")
                );
                rooms.add(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public boolean isRoomAvailable(int roomId, Date checkIn, Date checkOut) {
        return false;
    }

    public Room getRoomById(int id) {
        // Реализация получения комнаты по ID
        return null; // Placeholder return, actual implementation needed
    }

    public List<Room> getAvailableRooms() {
        return getAllRooms().stream()
            .filter(Room::isAvailable)
            .collect(Collectors.toList());
    }

    public List<Room> getRoomsByCategory(RoomCategory category) {
        return getAllRooms().stream()
            .filter(room -> room.getCategory() == category)
            .collect(Collectors.toList());
    }

    public List<Room> getRoomsByPriceRange(double minPrice, double maxPrice) {
        return getAllRooms().stream()
            .filter(room -> room.getPrice() >= minPrice && room.getPrice() <= maxPrice)
            .sorted((r1, r2) -> Double.compare(r1.getPrice(), r2.getPrice()))
            .collect(Collectors.toList());
    }
}
