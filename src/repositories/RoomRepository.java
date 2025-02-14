package repositories;

import models.*;
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
        String sql = "SELECT id, hotel_id, COALESCE(room_type, 'Unknown') AS room_type, " +
                "COALESCE(category, 'STANDARD') AS category, price, is_available FROM rooms";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Room> rooms = new ArrayList<>();
            while (rs.next()) {
                rooms.add(mapRoom(rs));
            }

            rooms.forEach(room -> System.out.println("🔍 Загружен номер ID: " + room.getId() +
                    " | Тип: " + room.getRoomType() + " | Категория: " + room.getCategory() +
                    " | Цена: " + room.getPrice()));

            return rooms;
        } catch (Exception e) {
            System.err.println("❌ Ошибка при получении всех номеров: " + e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Room> getRoomsByHotelId(int hotelId) {
        String sql = "SELECT r.id, r.hotel_id, COALESCE(r.room_type, 'Unknown') AS room_type, " +
                "r.price, r.is_available, COALESCE(rc.category, 'Unknown') AS category " +
                "FROM rooms r " +
                "LEFT JOIN room_categories rc ON r.category_id = rc.id " +
                "WHERE r.hotel_id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, hotelId);
            ResultSet rs = stmt.executeQuery();

            List<Room> rooms = new ArrayList<>();
            while (rs.next()) {
                rooms.add(mapRoom(rs));
            }

            rooms.forEach(room -> System.out.println("🔍 Загружен номер ID: " + room.getId() +
                    " | Тип: " + room.getRoomType() + " | Категория: " + room.getCategory() +
                    " | Цена: " + room.getPrice()));

            return rooms;
        } catch (Exception e) {
            System.err.println("❌ Ошибка при получении номеров по ID отеля: " + e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>();
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
            return rs.next() && rs.getInt(1) == 0;
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
                "LEFT JOIN room_categories rc ON r.category_id = rc.id " +
                "WHERE r.id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Room room = mapRoom(rs);
                System.out.println("🔍 Загружен номер ID: " + room.getId() +
                        " | Тип: " + room.getRoomType() + " | Категория: " + room.getCategory() +
                        " | Цена: " + room.getPrice());
                return room;
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка при получении номера по ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ✅ **Метод для маппинга данных из ResultSet в объект Room**
     */
    private Room mapRoom(ResultSet rs) throws Exception {
        String roomType = rs.getString("room_type");
        double price = rs.getDouble("price");
        boolean isAvailable = rs.getBoolean("is_available");
        RoomCategory category = RoomCategory.valueOf(rs.getString("category").toUpperCase());

        System.out.println("🔎 Создаем номер: " + roomType + " | Категория: " + category + " | Цена до обработки: " + price);

        // ✅ Определяем правильный класс номера
        Room room;
        if ("Deluxe".equalsIgnoreCase(roomType)) {
            room = new DeluxeRoom(rs.getInt("id"), rs.getInt("hotel_id"), price, isAvailable, category);
        } else if ("Suite".equalsIgnoreCase(roomType)) {
            room = new SuiteRoom(rs.getInt("id"), rs.getInt("hotel_id"), price, isAvailable, category);
        } else {
            room = new StandardRoom(rs.getInt("id"), rs.getInt("hotel_id"), price, isAvailable, category);
        }

        System.out.println("✅ Создан номер: " + room.getRoomType() + " | ID: " + room.getId() + " | Цена после обработки: " + room.getPrice());

        return room;
    }
}
