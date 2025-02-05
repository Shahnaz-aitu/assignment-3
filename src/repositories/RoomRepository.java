package repositories;

import models.Room;
import models.RoomCategory;
import repositories.interfaces.IRoomRepository;
import data.interfaces.IDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomRepository implements IRoomRepository {
    private final IDB db;

    public RoomRepository(IDB db) {
        this.db = db;
    }

    @Override
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT id, hotel_id, room_type, price, is_available FROM rooms";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("id"),
                        rs.getInt("hotel_id"),
                        rs.getString("room_type"),  // ✅ Исправлено: теперь тип номера загружается
                        rs.getDouble("price"),
                        rs.getBoolean("is_available"),
                        null
                ));
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка при получении всех номеров: " + e.getMessage());
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public List<Room> getRoomsByHotelId(int hotelId) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT id, hotel_id, room_type, price, is_available FROM rooms WHERE hotel_id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, hotelId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("id"),
                        rs.getInt("hotel_id"),
                        rs.getString("room_type"),  // ✅ Исправлено: теперь передается тип номера
                        rs.getDouble("price"),
                        rs.getBoolean("is_available"),
                        null
                ));
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка при получении номеров по ID отеля: " + e.getMessage());
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public boolean isRoomAvailable(int roomId, Date checkIn, Date checkOut) {
        String sql = "SELECT COUNT(*) FROM bookings " +
                "WHERE room_id = ? " +
                "AND (check_in_date < ? AND check_out_date > ?)";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            stmt.setDate(2, new java.sql.Date(checkOut.getTime()));
            stmt.setDate(3, new java.sql.Date(checkIn.getTime()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0;
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка при проверке доступности номера: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Room getRoomById(int id) {
        String sql = "SELECT id, hotel_id, room_type, price, is_available FROM rooms WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Room(
                        rs.getInt("id"),
                        rs.getInt("hotel_id"),
                        rs.getString("room_type"),  // ✅ Теперь загружается корректно
                        rs.getDouble("price"),
                        rs.getBoolean("is_available"),
                        null
                );
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка при получении номера по ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
