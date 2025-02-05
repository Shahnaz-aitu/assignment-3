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
        String sql = "SELECT id, hotel_id, COALESCE(room_type, 'Unknown') AS room_type, COALESCE(category, 'STANDARD') AS category, price, is_available FROM rooms";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String roomType = rs.getString("room_type");
                String category = rs.getString("category");

                System.out.println("🔍 Загружен номер ID: " + rs.getInt("id") + " | Тип: " + roomType + " | Категория: " + category);

                rooms.add(new Room(
                        rs.getInt("id"),
                        rs.getInt("hotel_id"),
                        roomType,
                        rs.getDouble("price"),
                        rs.getBoolean("is_available"),
                        RoomCategory.valueOf(category.toUpperCase())
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
        String sql = "SELECT r.id, r.hotel_id, COALESCE(r.room_type, 'Unknown') AS room_type, " +
                "r.price, r.is_available, COALESCE(rc.category, 'Unknown') AS category " +
                "FROM rooms r " +
                "LEFT JOIN room_categories rc ON r.category_id = rc.id " +  // ✅ Добавляем JOIN
                "WHERE r.hotel_id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, hotelId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String roomType = rs.getString("room_type"); // ✅ Загружаем тип номера
                String category = rs.getString("category");

                System.out.println("🔍 Загружен номер ID: " + rs.getInt("id") + " | Тип: " + roomType + " | Категория: " + category);

                rooms.add(new Room(
                        rs.getInt("id"),
                        rs.getInt("hotel_id"),
                        roomType, // ✅ Теперь передаем корректный тип
                        rs.getDouble("price"),
                        rs.getBoolean("is_available"),
                        RoomCategory.valueOf(category.toUpperCase())
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
        String sql = "SELECT r.id, r.hotel_id, COALESCE(r.room_type, 'Unknown') AS room_type, " +
                "r.price, r.is_available, COALESCE(rc.category, 'Unknown') AS category " +
                "FROM rooms r " +
                "LEFT JOIN room_categories rc ON r.category_id = rc.id " +  // ✅ Добавлен LEFT JOIN
                "WHERE r.id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String roomType = rs.getString("room_type");
                String category = rs.getString("category"); // ✅ Теперь правильно загружается категория

                System.out.println("🔍 Загружен номер ID: " + rs.getInt("id") + " | Тип: " + roomType + " | Категория: " + category);

                return new Room(
                        rs.getInt("id"),
                        rs.getInt("hotel_id"),
                        roomType,
                        rs.getDouble("price"),
                        rs.getBoolean("is_available"),
                        RoomCategory.valueOf(category.toUpperCase()) // ✅ Исправлено использование категории
                );
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка при получении номера по ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}