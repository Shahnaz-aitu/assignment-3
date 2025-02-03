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
        Connection conn = null;
        try {
            conn = db.getConnection();
            String sql = "SELECT COUNT(*) FROM bookings " +
                    "WHERE room_id = ? " +
                    "AND ((? BETWEEN start_date AND end_date) " +
                    "OR (? BETWEEN start_date AND end_date) " +
                    "OR (start_date BETWEEN ? AND ?))";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, roomId);
            st.setDate(2, new java.sql.Date(checkIn.getTime()));
            st.setDate(3, new java.sql.Date(checkOut.getTime()));
            st.setDate(4, new java.sql.Date(checkIn.getTime()));
            st.setDate(5, new java.sql.Date(checkOut.getTime()));

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0; // Если пересечений с другими бронированиями нет, комната доступна
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
