package repositories;

import models.Room;
import models.RoomCategory;
import repositories.interfaces.IRoomRepository;
import data.interfaces.IDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository implements IRoomRepository {
    private final IDB db;

    public RoomRepository(IDB db) {
        this.db = db;
    }

    @Override
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT r.id, r.hotel_id, r.type, r.price, r.is_available, rc.category " +
                "FROM rooms r " +
                "JOIN room_categories rc ON r.category_id = rc.id";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RoomCategory roomCategory = RoomCategory.valueOf(rs.getString("category").toUpperCase());
                Room room = new Room(
                        rs.getInt("id"),
                        rs.getInt("hotel_id"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_available"),
                        roomCategory
                );
                rooms.add(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public List<Room> getRoomsByHotelId(int hotelId) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT r.id, r.hotel_id, r.type, r.price, r.is_available, rc.category " +
                "FROM rooms r " +
                "JOIN room_categories rc ON r.category_id = rc.id " +
                "WHERE r.hotel_id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, hotelId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RoomCategory roomCategory = RoomCategory.valueOf(rs.getString("category").toUpperCase());
                Room room = new Room(
                        rs.getInt("id"),
                        rs.getInt("hotel_id"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_available"),
                        roomCategory
                );
                rooms.add(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public boolean isRoomAvailable(int roomId, java.util.Date checkIn, java.util.Date checkOut) {
        // Реализация проверки доступности номера (если необходимо)
        return false;
    }

    @Override
    public Room getRoomById(int id) {
        String sql = "SELECT r.id, r.hotel_id, r.type, r.price, r.is_available, rc.category " +
                "FROM rooms r " +
                "JOIN room_categories rc ON r.category_id = rc.id " +
                "WHERE r.id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                RoomCategory roomCategory = RoomCategory.valueOf(rs.getString("category").toUpperCase());
                return new Room(
                        rs.getInt("id"),
                        rs.getInt("hotel_id"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_available"),
                        roomCategory
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
